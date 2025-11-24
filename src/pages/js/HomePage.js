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
  window.location.href = "../login/login.html";
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
const statusAgendamentoEnum = {
  PENDENTE: "Pendente",
  CONFIRMADO: "Confirmado",
  CANCELADO: "Cancelado",
  FINALIZADO: "Finalizado"
};

// === FUNÇÃO PARA BUSCAR STATUS COM FETCH INCLUINDO AUTH HEADER ===
async function fetchStatusAgendamento() {
  try {
    const response = await fetch('http://localhost:8080/agendamento', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
      }
    });


    if (!response.ok) {
      throw new Error(`Erro na requisição: ${response.status}`);
    }
    console.log(await response.json())
    const data = await response.json();
    const statusAtual = data.status; // ajuste conforme a sua API
    const statusTexto = statusAgendamentoEnum[statusAtual] || "Desconhecido";

    const statusElemento = document.getElementById("status-texto");
    statusElemento.textContent = statusTexto;

  } catch (error) {
    console.error("Erro ao buscar status de agendamento:", error);
    const statusElemento = document.getElementById("status-texto");
    statusElemento.textContent = "Erro ao carregar status";
  }
}

// Chama a função quando a página carrega

fetchStatusAgendamento();

