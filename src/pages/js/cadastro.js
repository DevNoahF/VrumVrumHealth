
authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjM0ODk0MjgsImV4cCI6MTc2MzU3NTgyOH0.uwVI3j_yp-VX8OQU71eeCAMc4fzvcDxNhUvH9zBfMtDgeQ8zY1Z10GgGFhgVFoVRfrJTe2WjZNHjYN0A9O3xNQ"
// Espera o carregamento completo do DOM
document.addEventListener("DOMContentLoaded", () => {

  // Botões de necessidade especial
  const btnSim = document.getElementById("btn-sim");
  const btnNao = document.getElementById("btn-nao");
  const menuFlutuante = document.getElementById("menu-flutuante");

  // Mostra o campo "Qual?" quando clicar em "Sim"
  btnSim.addEventListener("click", () => {
    menuFlutuante.style.display = "block";
    btnSim.classList.add("ativo");
    btnNao.classList.remove("ativo");
  });

  // Esconde quando clicar em "Não"
  btnNao.addEventListener("click", () => {
    menuFlutuante.style.display = "none";
    btnNao.classList.add("ativo");
    btnSim.classList.remove("ativo");
  });

  // Validação do formulário
  const form = document.querySelector("form");

  form.addEventListener("submit", (event) => {
    event.preventDefault(); // Evita o envio imediato

    const firstname = document.getElementById("firstname").value.trim();
    const lastname = document.getElementById("lastname").value.trim();
    const cpf = document.getElementById("cpf").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmpassword = document.getElementById("confirmpassword").value;
    const dataNascimento = document.getElementById("borndata").value;
    const ddd = document.getElementById("ddd").value;
    const telefone= document.getElementById("number").value;
    const cep = document.getElementById("cep").value;
    const rua = document.getElementById("rua").value;
    const bairro = document.getElementById("bairro").value;
    const numero = document.getElementById("numero_casa").value

    // Verificação de campos obrigatórios
    if (!firstname || !lastname || !cpf || !email || !password || !confirmpassword) {
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
      cpf:String(cpf),
      dataNascimento:String(dataNascimento),
      ddd:Number(ddd),
      telefone:String(telefone),
      cep:String(cep),
      rua:String(rua),
      bairro:String(bairro),
      numero:String(numero)
    }

    // Exemplo de sucesso (futuro backend pode ser conectado aqui)
    const response = fetch("http://localhost:8080/paciente",{
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
        menuFlutuante.style.display = "none";
        btnSim.classList.remove("ativo");
        btnNao.classList.remove("ativo");
    })
  });
