using Jose;
using System;
using System.IO;
using System.Reflection;
using System.Collections.Generic;
using System.Net.Http;
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;
using CreativeCode.JWK.KeyParts;
using CreativeCode.JWK;
using CreativeCode.JWS;
using System.Text;

namespace JOSE
{
    class Program
    {
        private static JweRecipient recipientRsa1 => new JweRecipient(JweAlgorithm.RSA_OAEP, GetCertificate());
        static HttpClient client = new HttpClient();

        //private static string jwkJson = "{\r\n    \"kty\": \"RSA\",\r\n    \"n\": \"yeZxF0xDOJYfebxkpqU5aCP6Jc974uuB82wF1V7SghAkNFx1wvwvMODr73elJ1MafM55WnghfopgA9wq6NqGtrDGcN-1wLQ4y5Ht2Nc_3ylaVezqwsUbx0Dkbm98kefElHBw6xLHDtlxvLKcENWwAEBKm498Vd9gklpvkWEB6-rpZUMH7QNxjcX8IGZIKbgzxCXkUz05ckqgm1WEXjxwytnSbygP5ATGSXBf3bJUiElJUmDav77tgS1uJcMf5PT9kfPeBDp9ESSlqSmSQIFq1vwid45kBO6R-1fVuoNtZ2k_Qz_lIPK5LiX6RRHQGx1v5S26C58gzeLzZTIhXXaEcQ\",\r\n    \"e\": \"AQAB\",\r\n    \"d\": \"Dqv-2uURW3rZVbThkZSfK9i1dGr0A0T_AAwBDoTiAk6e-ukfK6i82y31J0VIDKgG3Dv66J6jPHlcydsFiT9cG8mT2h7_q73NBMoZgTD_NL3iES1yz-2X-65VC0txv36as_jJ0-Nc8NDPaZmHKtgEfrgtU8mlDO57K5eD1Qo6JAUgk6JqpBk3kGjCw0AYrRtHni0YfOJ54LjriAykmPJvcLAH2AaBW6uc9x5bGo_mwM1YNIFLZTuHcgLdirfaWT-xM0CGvlMPFuXp1kwhbFEuWtlkG7UzGQEM77o2RvuKYQpz8rZx4Xkw1m2HECj9cLTso9X4Nt5inMEeWJ9oHwbiGQ\",\r\n    \"p\": \"72uRhpgBslR5hRsO2wdvU0_TUQYSpdmmn19mHKSoVzW_i1frazcpV-WgH1qwazgx08dKp39FSkpc2azGvpsB-BHS5GH7NBAoyeGX8z97d0X995ZWqA6Ky8j_73bXeyZXfLPx5vF5TTOVaaPH9puqdzHLNoyMU7xL4LyNN6-Prc0\",\r\n    \"q\": \"1-G4Q2hP13yUkNJkFtJ2oriayLu2PYbOMABJWnhLG-oB7P9MQ81oA__sMRbGY7mapMYN2M99ZAZkqts5wz-uI6-bj0p6jYPD8UUA_G4VQwn6bxZc-AVTP45-0QxXqyk9I7W-d3UN2EIk-m7rNihuCNAOafjmX_7s4nS7V3lUrTU\",\r\n    \"dp\": \"y46BZBXJG32G9AxVTRO9KvArki-_mxK5_7Z0ctXr6nngKPDPT5DOrdSllwLcC8zzUEOYzV__4XHoD4o-T8AqpGdDDrnfghxbEYmkT0QiDP6NNvuzIZ97kleKgmZgPmwS5gt4GAomP-Ujm3WMhbdD76N-SxqkFG17dc_ZoQa6-eE\",\r\n    \"dq\": \"t6f1pJL3abE4isQSFkBSYTh_pcnbzhgDDV8RCIBSFZtMrBy8-umiVdTBUTm46VobL5a2bP6IBDJULe53A54HPaDFzG9mN0IjzfwQTLAyMmgov5zc1rthCS7mwF2pr5lTxC93rBTPBG2xG8SFIfvfMGwf9xOimYQ8bqM0RcSYvwk\",\r\n    \"qi\": \"tNoPcs-gl9gEVFrjU_P0AjM-anWVrrBab7386PpYL3e-H5iUXhqxogW9bwhRoEW93JDaK4f2NXa7AME4S14OIPg6Xt3mov5Z_DM0_CvVXYmOxOhTPsmC7RSA7xiRe4vQ6oEmk0Yoy5wjQ4NfNG-KLlD8K1m_GnxBCyDQqc3v4S4\"\r\n}";

        static void Main(string[] args)
        {
           
            //String xml = "<m1>Hello world!</m1>";
            var xml = File.ReadAllText("../../../Data/m1.xml");
            var jwkJson = File.ReadAllText("../../../Certificates/jwk.json");


            Console.WriteLine("Implementation of RFC7515 (JSON Web Signature)");

            // A signature key (RSA/EC/OCT) is needed. This implementation always uses JWKs (RFC7517) to supply a key.
            //var keyUse = PublicKeyUse.Signature;
            //var keyOperations = new HashSet<KeyOperation>(new[] { KeyOperation.ComputeDigitalSignature, KeyOperation.VerifyDigitalSignature });
            //var algorithm = Algorithm.ES256;
            //var jwk = new JWK(algorithm, keyUse, keyOperations); //TODO supply JWK from file instead of generating

            JWK jwk = new JWK(jwkJson);
           


            // Provide custom Content-Type and content. "application/jose+json" is only choosen as an example.
            // Create header based on supplied information. Exceptions may be thrown if required content is not proivided by the JWKProvider
            var joseHeader = new ProtectedJoseHeader(jwk, "application/jose", SerializationOption.JwsCompactSerialization);
            var payload = Encoding.UTF8.GetBytes(xml);

            // Initialize JWS
            var jws = new JWS(new[] { joseHeader }, payload);

            // Create digital signature
            jws.CalculateSignature();
            var jwsSignature = jws.Export();

            Console.WriteLine("Created JSON Web Signature: " + jwsSignature);

            //The other headers are set by Jose-jwt
            var sharedProtectedHeaders = new Dictionary<string, object>
            {
                { "cty", "xml"},
            };

            var recipients = new JweRecipient[] {
                recipientRsa1
            };
            String jwe = JWE.EncryptBytes(
                plaintext: Encoding.UTF8.GetBytes(jwsSignature),
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
