//adicionando dependencias necessarias
module.exports = { authToken}
const express = require("express")
const app = express()
const path = require("path")
const port = 8180;//porta de localhost, possível alterar?
const {Storage}= require('@google-cloud/storage')
const Multer = require('multer')
const fetch = require('node-fetch');

app.use(express.static(path.join(__dirname, "src", "pages")));

const multer = Multer({
    storage: Multer.memoryStorage(),
    limits:{
        fileSize: 20 * 1024 *1024//Arquivos não podem ser maiores de 20 mb
    }
})


let projectId="enviaranexo-475818";//Nome do projeto no google Cloud
let keyFilename='cred.json'//a chave do bucket, vai estar ignorada por padrão a chave, vocês terão que pegar e adicionar manualmente

const storage = new Storage({
    projectId,
    keyFilename
})
const bucket = storage.bucket('anexo-project')//Nome do bucket no google Clooud

//Tenta pegar o arquivo
app.get("/upload", async (req, res) => {
  try {
    const [files] = await bucket.getFiles();
    res.send([files]);
    console.log("Imagem carregada com sucesso");
  } catch (error) {
    res.send("Erro:" + error);
  }
});

//Envia a imagem para o google cloud
app.post("/upload", multer.single("enviar-anexo"), (req, res) => {
  console.log("Imagem carregada com sucesso");
  try {
    if (req.file) {
      console.log("Arquivo não encontrado");
      const blob = bucket.file(req.file.originalname);
      const blobStream = blob.createWriteStream();

      blobStream.on("finish", () => {
        res.status(200).send("Foi enviado no arquivo meu nobre");//Status 200 = Positivo :)
        console.log("Só sucesso nessa vida");
      });
      blobStream.end(req.file.buffer);
    } else throw "Erro com imagem, cabaço";
  } catch (error) {
    res.status(500).send(error);//Status 500= Negativo :(
  }
});

app.get('/', (req,res)=>{
  res.sendFile(path.join(__dirname, "src", "pages", "user", "pedido-transporte.html"));//local onde o aruqivo vai ser enviado
    //LEMBREM DE COLOCAR O DIRETÓRIO DE VOCÊS DE C: ATÉ O HTML DE PEDIDO-TRANSPORTE
})

app.listen(port,()=>{//Só uma mensagem quando for abrir o site localmente
    console.log('Servidor aberto em '+port)
});

async function obterToken() {
  try {
    let response = await fetch('http://localhost:8080/auth/register/adm-inicial', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        nome: "string",
        matricula: "string",
        email: "user@example.com",
        senha: "string"
      })
    });

    if (response.status === 400) {
      console.warn("POST deu 400, tentando GET");
      response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nome: "string",
          matricula: "string",
          email: "user@example.com",
          senha: "string"
      })
      });
    }

    if (!response.ok) {
      const erroTexto = await response.text();
      throw new Error(`Erro ao obter token: ${response.status} — ${erroTexto}`);
    }

    const dados = await response.json();
    return dados;

  } catch (error) {
    console.error("Erro ao obter token:", error);
    throw error;
  }
}


var authToken=obterToken()
  .then(dadosToken => {
    console.log("Token recebido:", dadosToken);
    // Aqui você pode salvar em localStorage, usar em outras requisições, etc.
  })
  .catch(err => {
    console.error("Erro ao chamar obterToken:", err);
  });
