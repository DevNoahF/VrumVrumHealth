document.getElementById("pedido-transporte").addEventListener("submit",async(e)=>{
    e.preventDefault();

    const FormDataOBJ=new FormData(e.target)
    const response= await fetch("http://localhost:8080/agendamento",{
        method:"POST",
        body:FormDataOBJ,
    });
    const result = await response.json();
    console.log(result)


});

const motivo=document.getElementById("motivo-value")
motivo.addEventListener("click", function(){//Função que permite o campo de frequencia aparecer e sumir, css apresenta uma transição simples
    

    console.log(this.value);
    if(this.value!="Tratamento Continuo"){
        frequencia=document.getElementsByClassName("frequencia")[0]
        frequencia.setAttribute("id","invi")
    }


    if(this.value=="Tratamento Contínuo"){
        var frequencia=document.getElementById("invi")
        frequencia.removeAttribute("id")
    }

})

var current_time= new Date()

const data_hora=document.getElementById("inputs")
data_hora.addEventListener("input",function(){//Função que proibe o usuário de colocar uma data no passado
    const data=document.getElementById("data").value
    const hora=document.getElementById("hora")


    const input_data= new Date(data)//Vários problemas, primeiro que o DIA colocado no site equivale a ao dia anterior, ent dia 13/10=12/10

    console.log(input_data)

    if(input_data>current_time){
        botao=document.getElementById("enviar").disable=true
        console.log("data válida")
        console.log(input_data)
    }

    if(input_data<=current_time || input_data==current_time){//Por algum motivo quando as datas são iguais mesmo com esse operador ele diz que a data é valida!!
        var botao=document.getElementById("enviar").disable=false//A ideia é que o botão não seja mais clicável, não sei dizer se está funcionando pq nem consigo enviar
        console.log("Não é possível colocar essa data")
    }

//O código em si funciona mas ele tá com esse problema de dar data errada
 
})
