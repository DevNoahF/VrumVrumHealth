import {getToken} from "./submit.js"
const authToken=await getToken()


  // Validação do formulário
  const form = document.querySelector("form");

  form.addEventListener("submit", (event) => {
    event.preventDefault(); // Evita o envio imediato

    const firstname = document.getElementById("firstname").value.trim();
    const lastname = document.getElementById("lastname").value.trim();
    const matricula = document.getElementById("matricula").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmpassword = document.getElementById("confirmpassword").value;





    // Verificação de campos obrigatórios
    if (!firstname || !lastname || !email || !password || !confirmpassword) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    // Verificação de senha
    if (password !== confirmpassword) {
      alert("As senhas não coincidem!");
      return;
    }

    var cadastro={
      nome:String(firstname+lastname),
      email:String(email),
      senha:String(password),
      matricula:String(matricula),
    }

    // Exemplo de sucesso (futuro backend pode ser conectado aqui)
    const response = fetch("http://localhost:8080/auth/register/adm",{
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${authToken}`
        },
        body:JSON.stringify(cadastro)
      });
      console.log(JSON.stringify(cadastro))
      
      if (response.status==200)
        alert(`Cadastro realizado com sucesso, ${firstname}!`);
        form.reset();
    })

  
