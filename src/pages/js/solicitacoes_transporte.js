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
    const dadosPaciente=pacienteData(dados.pacienteId)
    const dados= await agendamento.json()
    const dado_agendamento = {
      dataConsulta: dados.dataConsulta,
      horaConsulta: dados.horaConsulta,
      comprovante: dados.comprovante,
      localAtendimentoEnum: dados.localAtendimentoEnum,
      statusEnum: dados.statusEnum,
      retornoCasa: dados.retornoCasa,
      tipoAtendimentoEnum: dados.tipoAtendimentoEnum,
      frequencia:dados.frequenciaEnum,
      acompanhante: dados.acompanhante,
      pacienteId: dados.pacienteId,
      id:dados.id
    };

    idAgendamento.textContent=dado_agendamento.id
    nome.textContent+=(await dadosPaciente).nome
    data.textContent+=dado_agendamento.dataConsulta+("-")+dado_agendamento.horaConsulta
    nascimento.textContent+=(await dadosPaciente).dataNasc
    cpf.textContent+=(await dadosPaciente).cpf
    endereco.textContent+=(await dadosPaciente).rua+(await dadosPaciente).numero+(await dadosPaciente).bairro
    motivo.textContent+=dado_agendamento.tipoAtendimentoEnum
    unidade.textContent+=dado_agendamento.localAtendimentoEnum
    frequencia.textContent+=dado_agendamento.frequencia



}

async function pacienteData(id) {
    const paciente = await fetch("http://localhost:8080/paciente/"+id,{
        method: "GET",
        mode:'cors',
        headers: {"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
       

    })    
    const dados = paciente.json()
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

agendamentoData(1)