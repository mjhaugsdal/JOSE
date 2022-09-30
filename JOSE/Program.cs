using Jose;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;

namespace JOSE
{


    class Program
    {

        private static JweRecipient recipientRsa1 => new JweRecipient(JweAlgorithm.RSA_OAEP, GetCertificate());
        static HttpClient client = new HttpClient();

        static void Main(string[] args)
        {
           
            String xml = "<m1>Hello world!</m1>";
            byte[] payload = System.Text.Encoding.UTF8.GetBytes(xml);

            var sharedProtectedHeaders = new Dictionary<string, object>
            {
                { "cty", "xml"},
            };

            var recipients = new JweRecipient[] {
                recipientRsa1
            };
            String jwe = JWE.EncryptBytes(
                plaintext: payload,
                recipients: recipients,
                JweEncryption.A256GCM,
                mode: SerializationMode.Compact,
                extraProtectedHeaders: sharedProtectedHeaders);




            //Jose.RsaUsingSha rsaUsingSha = new RsaUsingSha("SHA256");
            //var signed = rsaUsingSha.Sign(jwe, key);

            
            Console.WriteLine(jwe);
            var response = send(jwe);
            var result = response.Result;



            //  NET
            //  Encoding: UTF-8
            //    Http-Method: POST
            //    Content-Type: application/json; charset=UTF-8
            //    Headers: {Content-Length=[490], content-type=[application/json; charset=UTF-8], Host=[localhost:8890]}
            // * 
            // * 
            // * 
            // *  JAVA
            // *  Address: http://localhost:8890/rest/m1
            //    Http-Method: POST
            //    Content-Type: application/jose
             //  Headers: {Content-Type=[application/jose], Content-Encoding=[UTF-8], Accept=[]}
             //
             //

            Console.WriteLine("Hello world!");
            

        }


        private static RSA GetCertificate()
        {
            return X509().GetRSAPublicKey();
        }

        private static X509Certificate2 X509()
        {
            return new X509Certificate2("C:/Users/haugsmar/source/repos/JOSE/JOSE/Certificates/server.p12", "changeit");
            //TODO trenger server sertifikatet
            //return new X509Certificate2("Certificates/server.p12", "1", X509KeyStorageFlags.Exportable | X509KeyStorageFlags.MachineKeySet);
        }

        static async Task<Uri> send(String json)
        {

            StringContent stringContent = new StringContent(json, System.Text.Encoding.UTF8, "application/jose");
            HttpResponseMessage response = await client.PostAsync("http://localhost:8890/rest/m1", stringContent);
            response.EnsureSuccessStatusCode();

            // return URI of the created resource.
            return response.Headers.Location;
        }
    }
}
