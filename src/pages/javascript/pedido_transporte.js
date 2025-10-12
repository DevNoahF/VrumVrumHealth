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