//Inicialização de atributos e variáveis
const frequencia_value=document.getElementById("frequencia-value")
frequencia_value.disabled=true
frequencia_value.value=null
const frequencia=document.getElementsByClassName("frequencia")[0];

const motivo=document.getElementById("motivo-value")


motivo.addEventListener("change",function(){
    if(this.value!="Tratamento Contínuo"){
        frequencia.setAttribute("id","invi")

        frequencia_value.disabled=true//Desabilita o botão se não for tratamento contínuo

        frequencia_value.value=null//Coloca como nulo se não for tratamento

        console.log(frequencia_value.value)
    }else{
        var freqId=document.getElementById("invi")

        freqId.removeAttribute("id")

        frequencia_value.disabled=false

    }
})

//Função de enviar informações para banco de dados
document.getElementById("pedido-transporte").addEventListener("submit", async (e) => {
  e.preventDefault();

  const data = {
    dataConsulta: String(document.getElementById("data").value),
    horaConsulta: document.getElementById("hora").value + ":00", 
    comprovante: null,
    localAtendimentoEnum: String(document.getElementById("unidade-value").value), 
    statusEnum: "PENDENTE",
    retornoCasa: true, 
    pacienteId: 1
  };

  const response = await fetch("http://localhost:8080/agendamento", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: data,
  });
})
