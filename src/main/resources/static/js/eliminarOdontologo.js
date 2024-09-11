const advertenciaM = new bootstrap.Modal(document.getElementById("staticBackdrop"));

deleteOdontologo = function (id) {
    currentOdontologoId = id;
    advertenciaM.show();
}

document.getElementById("confirmarEliminarBtn").addEventListener("click", () => {
    eliminarOdontologo();
});

eliminarOdontologo = function () {
    fetch(`${apiURL}/odontologo/eliminar/${currentOdontologoId}`, {
        method: "DELETE",
    })
        .then((response) => response.json())
        .then((data) => {
            advertenciaM.hide();
            console.log(data);
            mostrarModal("El odontologo fue eliminado con exito" );
            fetchOdontologos();
        })
        .catch((error) => {
            console.error("Error borrando odontologo:", error);
        });
};