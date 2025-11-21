document.querySelectorAll(".menu a").forEach(btn => {
    btn.addEventListener("click", function(e) {

        // ==================== RIPPLE ====================
        const oldRipple = this.querySelector(".ripple");
        if (oldRipple) oldRipple.remove();

        const x = e.clientX - this.getBoundingClientRect().left;
        const y = e.clientY - this.getBoundingClientRect().top;

        const ripple = document.createElement("span");
        ripple.classList.add("ripple");
        ripple.style.left = `${x}px`;
        ripple.style.top = `${y}px`;

        this.appendChild(ripple);

        setTimeout(() => ripple.remove(), 450);

        // ==================== SCROLL PARA SEÇÃO ====================
        const targetId = this.getAttribute("data-target"); // pega o id da seção
        if (targetId) {
            e.preventDefault(); // evita o # padrão
            const targetSection = document.getElementById(targetId);
            targetSection.scrollIntoView({ behavior: "smooth" });
        }
    });
});
