import { getToken } from "./submit.js";

const authToken = await getToken();

// === ÍCONES FEATHER ===
feather.replace();

// === MENU LATERAL: SELECIONAR ITEM ===
const menuItems = document.querySelectorAll(".menu li");
menuItems.forEach(item => {
  item.addEventListener("click", () => {
    menuItems.forEach(i => i.classList.remove("ativo"));
    item.classList.add("ativo");
  });
});

// === BLOCO EXPANSÍVEL (ACCORDION) ===
const blocos = document.querySelectorAll(".bloco");
blocos.forEach(bloco => {
  const titulo = bloco.querySelector(".bloco-titulo");
  const conteudo = bloco.querySelector(".bloco-conteudo");
  const icone = titulo.querySelector("i");

  titulo.addEventListener("click", () => {
    conteudo.classList.toggle("ativo");
    icone.classList.toggle("rotate");
  });
});

// === BOTÃO DE LOGOUT ===
const logoutBtn = document.querySelector(".logout-btn");
logoutBtn.addEventListener("click", () => {
  alert("Você saiu da sua conta.");
  window.location.href = "../login.html";
});

document.getElementById("solicitar").addEventListener("click", function(e) {
  e.preventDefault();
  window.location.href = "./pedidoTransporte.html";
});
document.getElementById("consultar").addEventListener("click", function(e) {
  e.preventDefault();
  window.location.href = "./statusSolicitacao.html";
});

// === ENUM DE STATUS AGENDAMENTO ===


const pacienteId=sessionStorage.getItem("pacienteId")

var estado = document.getElementById("estado")

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
      statusComprovanteEnum: dados.statusComprovanteEnum,
    };

    estado.textContent+=dado_agendamento.statusComprovanteEnum



   


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







getAgendamento(pacienteId)

