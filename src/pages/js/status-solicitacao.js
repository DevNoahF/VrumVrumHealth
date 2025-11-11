authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjI4MjExODUsImV4cCI6MTc2MjkwNzU4NX0.5iQBCtjDUWbA2LdM74E6OJ_6s7WtV6p_F7Eerz94IWQYoQL_hnWYkKVbZ4WDIO9jL5Wu8DlQ1CZuqgH11tPiHg"
var unidade=document.getElementById("unidade")
var estado = document.getElementById("status")


async function getAgendamento(id){//Precisa de id agendamento
    const get_agendamento = await fetch("http://localhost:8080/agendamento/"+ id, {
      method: "GET",
      mode:'cors',
      headers: {"Content-Type": "application/json",
         'Authorization': `Bearer ${authToken}`
       },
    })
    const dados= await get_agendamento.json()

    const dado_agendamento = {
      id: dados.id,
      dataConsulta: dados.dataConsulta,
      horaConsulta: dados.horaConsulta,
      comprovante: dados.comprovante,
      localAtendimentoEnum: dados.localAtendimentoEnum,
      statusEnum: dados.statusEnum,
      retornoCasa: dados.retornoCasa,
      tratamentoContinuo: dados.tratamentoContinuo,
      tipoAtendimentoEnum: dados.tipoAtendimentoEnum,
      frequencia: dados.frequencia,
      acompanhante: dados.acompanhante,
      pacienteId: dados.pacienteId
    };
   

    unidade.textContent=unidade.textContent+String(dado_agendamento.localAtendimentoEnum)
    estado.textContent=estado.textContent+dado_agendamento.statusEnum
    return dado_agendamento
}


console.log(getAgendamento(1))

