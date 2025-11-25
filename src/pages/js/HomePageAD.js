// ========================================
// ÍCONES FEATHER
// ========================================
feather.replace();

// ========================================
// DESTACAR ITEM ATIVO DO MENU
// ========================================
const menuItems = document.querySelectorAll(".menu li");

menuItems.forEach(item => {
    item.addEventListener("click", () => {
        menuItems.forEach(i => i.classList.remove("ativo"));
        item.classList.add("ativo");
    });
});

// ========================================
// BLOCOS EXPANSÍVEIS (como no paciente)
// ========================================
const blocos = document.querySelectorAll(".bloco");

blocos.forEach(bloco => {
    const titulo = bloco.querySelector("h2");
    const conteudo = bloco.querySelector(".lista-links");

    // Primeiro deixa tudo recolhido
    conteudo.style.maxHeight = "0px";
    conteudo.style.overflow = "hidden";
    conteudo.style.transition = "max-height 0.3s ease";
    
    titulo.style.cursor = "pointer";

    titulo.addEventListener("click", () => {
        if (conteudo.style.maxHeight === "0px") {
            conteudo.style.maxHeight = conteudo.scrollHeight + "px";
        } else {
            conteudo.style.maxHeight = "0px";
        }
    });
});

// ========================================
// CONFIRMAÇÃO AO SAIR
// ========================================
const logoutBtn = document.querySelector(".logout-btn");

logoutBtn.addEventListener("click", () => {
    const certeza = confirm("Deseja realmente sair do sistema?");
    if (certeza) {
        window.location.href = "../login/login.html"; 
    }
});

function direcionarPagina(link) {
    window.location.href = String(link);
}

document.getElementById("pendentes").addEventListener("click",function(e){
    e.preventDefault()
    window.location.href = "./solicitacoesTransporte.html";
})

document.getElementById("aprovadas").addEventListener("click",function(e){
    e.preventDefault()
    window.location.href = "./pacientesAprovados.html";
})

document.getElementById("atribuir").addEventListener("click",function(e){
    e.preventDefault()
    window.location.href = "./atribuirTransporte.html";
})

