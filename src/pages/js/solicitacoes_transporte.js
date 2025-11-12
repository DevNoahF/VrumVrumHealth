authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjI5MTY0ODYsImV4cCI6MTc2MzAwMjg4Nn0.RtQb8W_cOfrD21ZyBIZ6Xv7toNAy84sooTS9uzEiAYre2YOw74svwsIl2KApiAV1-gVVRJ7xjI7QV-OXsVt4VQ"
var idAgendamento=document.getElementById("id")
var nome=document.getElementById("nome")
var data=document.getElementById("data")
var nascimento=document.getElementById("nascimento")
var cpf=document.getElementById("cpf")
var endereco=document.getElementById("endereco")
var motivo=document.getElementById("motivo")
var unidade=document.getElementById("unidade")
var frequencia=document.getElementById("frequencia")
var acompanhante=document.getElementById("acompanhante")
var transporte_volta=document.getElementById("transporte-volta")


async function agendamentoData(id){
    const agendamento = await fetch("http://localhost:8080/agendamento/"+id,{
        method: "GET",
        mode:'cors',
        headers: {"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
       

    })
    const dados= await agendamento.json()
    const dado_agendamento = {
      dataConsulta: dados.dataConsulta,
      horaConsulta: dados.horaConsulta,
      comprovante:dados.comprovante,
      localAtendimentoEnum: dados.localAtendimentoEnum,
      statusEnum: dados.statusEnum,
      retornoCasa: dados.retornoCasa,
      tratamentoContinuo:dados.tratamentoContinuo,
      tipoAtendimentoEnum: dados.tipoAtendimentoEnum,
      frequencia:dados.frequencia,
      acompanhante: dados.acompanhante,
      pacienteId: dados.pacienteId,
      id:dados.id,
      estado:dados.statusEnum
    };




    return dado_agendamento

}

async function mostrarMensagem(id){
  const dadosPaciente=await pacienteData(id)
  const dadosAgendamento=await agendamentoData(id)
    idAgendamento.textContent=("#")+dadosAgendamento.id
    nome.textContent+=(await dadosPaciente).nome
    data.textContent+=dadosAgendamento.dataConsulta+("-")+dadosAgendamento.horaConsulta
    nascimento.textContent+=(await dadosPaciente).dataNasc
    cpf.textContent+=(await dadosPaciente).cpf
    endereco.textContent+=(await dadosPaciente).rua+(" ")+(await dadosPaciente).numero+(" ")+(await dadosPaciente).bairro
    motivo.textContent+=dadosAgendamento.tipoAtendimentoEnum
    unidade.textContent+=dadosAgendamento.localAtendimentoEnum
    frequencia.textContent+=dadosAgendamento.frequencia
    acompanhante.textContent+=changeValue(dadosAgendamento.acompanhante)
    transporte_volta.textContent+=changeValue(dadosAgendamento.retornoCasa)
}

async function pacienteData(id) {
    const paciente = await fetch("http://localhost:8080/paciente/"+id,{
        method: "GET",
        mode:'cors',
        headers: {"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
       

    })    
    const dados = await paciente.json()
    const pacienteData={
        nome:dados.nome,
        dataNasc:dados.dataNascimento,
        cpf:dados.cpf,
        bairro:dados.bairro,
        rua:dados.rua,
        numero:dados.numero
    }
    return pacienteData
}
function changeValue(valor) {

  if(valor==true){
    return "Sim"
  }
  if(valor==false){
    return "Não"
  }
  return "Erro, dado não encontrado";
  
}

async function verAnexo(id) {
  comprovante=await agendamentoData(id)
  const divAnexo = document.getElementById("anexo");
  if (comprovante.comprovante) {
    divAnexo.onclick = () => {
      // abre em nova aba, por exemplo
      window.open(comprovante.comprovante, "_blank");
    };
    console.log(comprovante.comprovante)
  }
  
}

async function aprovar(id){
  agendamentoAprovado = await agendamentoData(id)
  document.getElementById("aprovar").addEventListener("click",async function(){
    agendamentoAprovado.statusEnum="APROVADO"
    const aprovar = await fetch("http://localhost:8080/agendamento/"+id,{
      method:"PUT",
      mode:"cors",
      headers:{"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
       body:JSON.stringify(agendamentoAprovado)

    })
    console.log(agendamentoAprovado)
  })
}

async function recusar(id){
  agendamentoAprovado = await agendamentoData(id)
  document.getElementById("recusar").addEventListener("click",async function(){
    agendamentoAprovado.statusEnum="NEGADO"
    const aprovar = await fetch("http://localhost:8080/agendamento/"+id,{
      method:"PUT",
      mode:"cors",
      headers:{"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
       body:JSON.stringify(agendamentoAprovado)

    })
    console.log(agendamentoAprovado)
  })
}

//Isso aqui precisa de uma função para colocar o id
mostrarMensagem(1)
aprovar(1)
recusar(1)
verAnexo(1)