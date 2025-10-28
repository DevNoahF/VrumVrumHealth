//Inicialização de atributos e variáveis
const unidadeValue=document.getElementById("unidade-value")

const frequencia_value=document.getElementById("frequencia-value")
frequencia_value.disabled=true

const motivo=document.getElementById("motivo-value")

const acompanhanteValue=document.getElementById("acompanhante-value")

const horaValue=document.getElementById("hora")
const dataValue=document.getElementById("data")

const frequencia=document.getElementsByClassName("frequencia")[0];//Serve para deixar invisível 

const submit = document.getElementById("enviar")
setNull()

//Função que deixa todos os campos em null automaticamente
function setNull(){
  const inputs=document.querySelectorAll("#unidade-value, #frequencia-value,#motivo-value,#acompanhante-value,#data,#hora")
  inputs.forEach(input=>{
    input.value=null;
  })

}

//Verifica se não há nenhum campo vazio e retorna true or false
function verify_data(){
  const inputs = document.querySelectorAll("#unidade-value,#motivo-value,#acompanhante-value,#data,#hora")

  for(let i=0; i<inputs.length; i++){
    if(inputs[i].value==null||inputs[i].value==""||inputs[i].value==undefined){
      return true;
  }else{
    return false;
  }
}}


//Função para pegar o valor dos botões de Sim e Não
function getRadioValue(name){
  const radios=document.getElementsByName(name)
  let valor=null;
  for(let i =0; i<radios.length; i++)
    if(radios[i].checked){
      return valor=radios[i].value;
    }

}

//Função para tratamento de dados, tranformar sim e não em True e False
function changeRadioValue(valor){
  if(valor=="Não")
    return false;

  if(valor=="Sim")
    return true;

  if(valor!="Sim" && valor!="Não")
    return null
}
//console.log(changeRadioValue(getRadioValue("acompanhante?")))-->Exemplo de Comando


//Função para detectar mudança em motivo e aparecer frequencia se necessário
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

  console.log(verify_data())
  
  if(verify_data()==true){
    alert("Campo não preenchido")
  }
  if(verify_data()==false){
    console.log("campo preenchido pó mandar")

//variável que organiza o json
    const data = {
      "dataConsulta":dataValue.value,
      "horaConsulta":horaValue.value+":00",
      "comprovante":sendFile(),
      "localAtendimento":unidadeValue.value,
      "frequencia":frequencia_value.value,
      "retornoCasa":changeRadioValue(getRadioValue("transporte-volta?")),
      "acompanhante":changeRadioValue(getRadioValue("acompanhante?")),
      "paciente_id": 0

    };
    console.log(JSON.stringify(data))//Stringfy deixa trata a variável data e deixa no jeito para enviar um JSON
    console.log(data.comprovante)
    const response = await fetch("http://localhost:8080/agendamento", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
        
  }

})


