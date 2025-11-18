// === ÍCONES FEATHER ===
feather.replace();

// === MENU LATERAL: SELECIONAR ITEM ===
const menuItems = document.querySelectorAll(".menu li");

menuItems.forEach(item => {
    item.addEventListener("click", () => {
        // Remove seleção anterior
        menuItems.forEach(i => i.classList.remove("ativo"));
        // Ativa o atual
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
    window.location.href = "../login/login.html"; // Ajuste se seu caminho for outro
});

document.getElementById("solicitar").addEventListener("click",function(e){
    e.preventDefault()
    window.location.href = "./pedidoTransporte.html";
})

document.getElementById("consultar").addEventListener("click",function(e){
    e.preventDefault()
    window.location.href="./statusSolicitacao.html"
})
