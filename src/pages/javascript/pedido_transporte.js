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


const data_hora=document.getElementById("inputs")
data_hora.addEventListener("input",function(){//Função que proibe o usuário de colocar uma data no passado
    const data=document.getElementById("data").value
    const hora=document.getElementById("hora")


    const input_data= new Date(data)


    if(data<data_format){
        console.log("Não é possível colocar essa data")
    }

 
})
