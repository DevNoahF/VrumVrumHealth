//adicionando dependencias necessarias
const express = require("express")
const app = express()
const port = 8080;//porta de localhost, possível alterar?
const {Storage}= require('@google-cloud/storage')

let projectId="anexo-project";//Nome do projeto no google Cloud
let keyFilename='cred.json'//a chave do bucket, vai estar ignorada por padrão a chave, vocês terão que pegar e adicionar manualmente

const storage = new Storage({
    projectId,
    keyFilename
})

app.get('/', (req,res)=>{
    res.sendFile('C:/Users/Perin/Documents/GitHub/VrumVrumHealth/src/pages/user/pedido-transporte.html')//local onde o aruqivo vai ser enviado
    //LEMBREM DE COLOCAR O DIRETÓRIO DE VOCÊS DE C: ATÉ O HTML DE PEDIDO-TRANSPORTE
})

app.listen(port,()=>{//Só uma mensagem quando for abrir o site localmente
    console.log('Servidor aberto em '+port)
});




