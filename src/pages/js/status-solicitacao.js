
var nome=document.getElementById("paciente")
var endereco= document.getElementById("endereco")
var unidade=document.getElementById("unidade")
var estado = document.getElementById("status")
var acompanhante = document.getElementById("acompanhante")
var retorno=document.getElementById("retorno")
var motivo=document.getElementById("tipo-tratamento")
var frequencia=document.getElementById("frequencia")
var dia= document.getElementById("data")

//Função pega dados de agendamento
async function getAgendamento(id){//Precisa de id agendamento
    const get_agendamento = await fetch("http://localhost:8080/agendamento/"+ id, {
      method: "GET",
      mode:'cors',
      headers: {"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
    })

    const dados= await get_agendamento.json()
    const pacienteDado=getPaciente(dados.pacienteId)

    const dado_agendamento = {
      dataConsulta: dados.dataConsulta,
      horaConsulta: dados.horaConsulta,
      comprovante: dados.comprovante,
      localAtendimentoEnum: dados.localAtendimentoEnum,
      statusEnum: dados.statusEnum,
      retornoCasa: dados.retornoCasa,
      tipoAtendimentoEnum: dados.tipoAtendimentoEnum,
      frequencia:dados.frequencia,
      acompanhante: dados.acompanhante,
      pacienteId: dados.pacienteId
    };


   
    nome.textContent+=(await pacienteDado).nome
    endereco.textContent+=(await pacienteDado).rua+(" ")+(await pacienteDado).numero+(" ")+(await pacienteDado).bairro
    unidade.textContent+=dado_agendamento.localAtendimentoEnum
    estado.textContent+=dado_agendamento.statusEnum
    motivo.textContent+=dado_agendamento.tipoAtendimentoEnum
    frequencia.textContent+=dado_agendamento.frequencia
    dia.textContent+=dado_agendamento.dataConsulta+"-"+dado_agendamento.horaConsulta
    acompanhante.textContent+=changeValue(dado_agendamento.acompanhante)
    retorno.textContent+=changeValue(dado_agendamento.retornoCasa)

      const divAnexo = document.getElementById("visualizar-anexo");
  if (dado_agendamento.comprovante) {
    divAnexo.onclick = () => {
      // abre em nova aba, por exemplo
      window.open(dado_agendamento.comprovante, "_blank");
    };
  }

    return dado_agendamento
}

//Pega dados de Paciente
async function getPaciente(id) {
  const get_paciente = await fetch("http://localhost:8080/paciente/"+id,{
    method: "GET",
    mode:'cors',
    headers: {"Content-Type": "application/json",
      'Authorization': `Bearer ${authToken}`
    },
  })
  const dados= await get_paciente.json()
  const dadosPaciente={
    nome:dados.nome,
    rua:dados.rua,
    bairro:dados.bairro,
    numero:dados.numero
  }
  return dadosPaciente
}
async function cancelarAgendamento(id){
  document.getElementById("cancelar").addEventListener("click", async function(){
    const delPaciente= await fetch("http://localhost:8080/agendamento/"+id,{
      method:"DELETE",
      mode:"cors",
      headers: {"Content-Type": "application/json",
      'Authorization': `Bearer ${authToken}`
    },
    })
  })
}

//Mudar os valores de true e false para Sim e não
function changeValue(valor) {

  if(valor==true){
    return "Sim"
  }
  if(valor==false){
    return "Não"
  }
  return "Erro, dado não encontrado";
  
}




console.log(getAgendamento(2))

cancelarAgendamento(1)

