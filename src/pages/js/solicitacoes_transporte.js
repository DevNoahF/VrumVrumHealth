authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjI5MTM4ODMsImV4cCI6MTc2MzAwMDI4M30._HdU4dLVhN62Fy7j3PPT91I6wz4VnpPRkoy7D_vBBcjNqflWTq-7qBKAmWUWFB1x1ErCE8EA8u4rMIqNX-SbUw"
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
    const dadosPaciente=await pacienteData(1)
    console.log(dadosPaciente)
    const dados= await agendamento.json()
    const dado_agendamento = {
      dataConsulta: dados.dataConsulta,
      horaConsulta: dados.horaConsulta,
      comprovante: dados.comprovante,
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
  console.log(dado_agendamento)

    idAgendamento.textContent=("#")+dado_agendamento.id
    nome.textContent+=(await dadosPaciente).nome
    data.textContent+=dado_agendamento.dataConsulta+("-")+dado_agendamento.horaConsulta
    nascimento.textContent+=(await dadosPaciente).dataNasc
    cpf.textContent+=(await dadosPaciente).cpf
    endereco.textContent+=(await dadosPaciente).rua+(" ")+(await dadosPaciente).numero+(" ")+(await dadosPaciente).bairro
    motivo.textContent+=dado_agendamento.tipoAtendimentoEnum
    unidade.textContent+=dado_agendamento.localAtendimentoEnum
    frequencia.textContent+=dado_agendamento.frequencia
    acompanhante.textContent+=changeValue(dado_agendamento.acompanhante)
    transporte_volta.textContent+=changeValue(dado_agendamento.retornoCasa)

    return dado_agendamento

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

agendamentoData(1)
aprovar(1)

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