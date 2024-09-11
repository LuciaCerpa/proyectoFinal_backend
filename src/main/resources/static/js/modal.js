function mostrarModal(mensaje) {
    const modalHTML = `
        <div class="modal fade" id="dynamicModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="dynamicModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-fullscreen-sm-down">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 class="modal-title fs-5" id="dynamicModalLabel" style="color: green;">SUCCESS!</h2>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h6 class="text-center">${mensaje}</h6>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    const modalContainer = document.createElement('div');
    modalContainer.innerHTML = modalHTML;
    document.body.appendChild(modalContainer);

    const dynamicModal = new bootstrap.Modal(document.getElementById('dynamicModal'));
    dynamicModal.show();

    document.getElementById('dynamicModal').addEventListener('hidden.bs.modal', function () {
        modalContainer.remove();
    });
}