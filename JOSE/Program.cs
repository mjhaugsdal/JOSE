using Jose;
using System;
using System.IO;
using System.Reflection;
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
           
            //String xml = "<m1>Hello world!</m1>";
            var xml = File.ReadAllText("../../../Data/m1.xml");

            
            byte[] payload = System.Text.Encoding.UTF8.GetBytes(xml);

            //The other headers are set by Jose-jwt
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

            
            Console.WriteLine(jwe); //the token, go decode it at jwt.io or something
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

            Console.WriteLine(result); //Here should just be encrypted JWE

            var decrypted = JWE.Decrypt(result, PrivKey());
            
            Console.WriteLine(decrypted.Plaintext); //Here should be plaintext

        }

        private static RSA PrivKey()
        {
            return X509Private().GetRSAPrivateKey();
        }

        private static RSA GetCertificate()
        {
            return X509Public().GetRSAPublicKey();
        }

        //Relative paths in .net .. 
        private static X509Certificate2 X509Public()
        {
            return new X509Certificate2("../../../Certificates/server.cer", "changeit");
        }

        private static X509Certificate2 X509Private()
        {
            return new X509Certificate2("../../../Certificates/client.p12", "changeit");
        }

        static async Task<string> send(String json)
        {
            StringContent stringContent = new StringContent(json, System.Text.Encoding.UTF8, "application/jose");
            HttpResponseMessage response = await client.PostAsync("http://localhost:8890/rest/m1", stringContent);
            response.EnsureSuccessStatusCode();

            // return URI of the created resource.
            return await response.Content.ReadAsStringAsync();
        }
    }
}
