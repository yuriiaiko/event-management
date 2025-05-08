// Script pour l'application de gestion des événements EMSI

document.addEventListener("DOMContentLoaded", () => {
    // Activer les tooltips Bootstrap
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map((tooltipTriggerEl) => new bootstrap.Tooltip(tooltipTriggerEl))

    // Filtrage des événements
    const searchInput = document.getElementById("searchInput")
    const filterType = document.getElementById("filterType")
    const sortBy = document.getElementById("sortBy")

    if (searchInput && filterType && sortBy) {
        const eventCards = document.querySelectorAll(".event-card")

        searchInput.addEventListener("input", filterEvents)
        filterType.addEventListener("change", filterEvents)
        sortBy.addEventListener("change", sortEvents)

        function filterEvents() {
            const searchTerm = searchInput.value.toLowerCase()
            const filterValue = filterType.value

            eventCards.forEach((card) => {
                const title = card.querySelector(".card-title").textContent.toLowerCase()
                const description = card.querySelector(".card-text").textContent.toLowerCase()
                const isPaid = card.querySelector(".badge").textContent.includes("Payant")

                const matchesSearch = title.includes(searchTerm) || description.includes(searchTerm)
                const matchesFilter =
                    filterValue === "all" || (filterValue === "free" && !isPaid) || (filterValue === "paid" && isPaid)

                card.closest(".col").style.display = matchesSearch && matchesFilter ? "block" : "none"
            })
        }

        function sortEvents() {
            const sortValue = sortBy.value
            const container = document.querySelector(".row-cols-1")
            const items = Array.from(container.children)

            items.sort((a, b) => {
                if (sortValue === "date") {
                    const dateA = a.querySelector(".bi-calendar").nextElementSibling.textContent
                    const dateB = b.querySelector(".bi-calendar").nextElementSibling.textContent
                    return new Date(dateA) - new Date(dateB)
                } else {
                    const titleA = a.querySelector(".card-title").textContent
                    const titleB = b.querySelector(".card-title").textContent
                    return titleA.localeCompare(titleB)
                }
            })

            items.forEach((item) => container.appendChild(item))
        }
    }

    // Validation des formulaires
    const forms = document.querySelectorAll(".needs-validation")

    Array.from(forms).forEach((form) => {
        form.addEventListener(
            "submit",
            (event) => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add("was-validated")
            },
            false,
        )
    })

    // Confirmation de désinscription
    const desinscriptionForm = document.querySelector('form[action*="desinscription"]')

    if (desinscriptionForm) {
        desinscriptionForm.addEventListener("submit", (event) => {
            if (!confirm("Êtes-vous sûr de vouloir vous désinscrire de cet événement ?")) {
                event.preventDefault()
            }
        })
    }
})
