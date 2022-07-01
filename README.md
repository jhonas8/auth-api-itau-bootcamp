# auth-api-itau-bootcamp
Api criada para validar requisições do bootcamp-itaú

Rota de validação do token
-POST (/api/itau/auth/validate):
  body {
   "token": "string" 
  }
  retorna 
    {
    	"errorMessage": null | string,
      "authenticated": boolean,
      "id": number,
      "roles": string[]
  }
  
  Rota de validação do login
  -POST (/api/itau/auth/signin):
    body {
      "username":"string",
      "password": "string"
    }
    retorna
      {
        "id": number,
        "roles": string[],
        "token": string
      }
      
 Rota de registro
 -POST (/api/itau/auth/signup):
    body {
      "username":"user",
      "email": "userNew@gmail.com",
      "password": "testingpassword",
      "firstName": "joao",
      "lastName": "melo"
    }
    
    retorna {
        "message": "User was registered!"
    }
    
 O servidor está conectado a uma instância online de um banco de dados MySQL, cujo max_length_users por padrão é de 5.
 A implementação do cache foi iniciada, mas não finalizada por completo (seguindo o mesmo padrão de qualidade do código)
 e por isso não está commitada no atual repositório.
 
####O modelo de todo o retorno considera um status code 200####
