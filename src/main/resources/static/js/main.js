/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
document.addEventListener('DOMContentLoaded', function() {
    // Añadir efecto de aparición escalonada a las tarjetas
    const taskCards = document.querySelectorAll('.task-card');
    taskCards.forEach((card, index) => {
        card.style.opacity = "0";
        card.style.transform = "translateY(20px)";
        
        setTimeout(() => {
            card.style.transition = "opacity 0.5s ease, transform 0.5s ease";
            card.style.opacity = "1";
            card.style.transform = "translateY(0)";
        }, 100 + (index * 50)); // Retraso escalonado
    });

    // Añadir clases CSS a las tarjetas de tareas según su estado
    document.querySelectorAll('.card-body .card, .task-card').forEach(card => {
        const columnId = card.closest('.task-column')?.id;
        
        if (columnId) {
            // Removemos primero todas las clases de estado
            card.classList.remove('pending', 'in-progress', 'completed');
            
            // Añadimos la clase correspondiente según la columna
            if (columnId === 'column-PENDIENTE') {
                card.classList.add('task-card', 'pending');
            } else if (columnId === 'column-EN_PROGRESO') {
                card.classList.add('task-card', 'in-progress');
            } else if (columnId === 'column-COMPLETADA') {
                card.classList.add('task-card', 'completed');
            }
        } else {
            // Método alternativo basado en el texto de estado
            const statusText = card.querySelector('.status-text');
            if (statusText) {
                const status = statusText.textContent.trim();
                if (status === 'PENDIENTE') {
                    card.classList.add('task-card', 'pending');
                } else if (status === 'EN_PROGRESO') {
                    card.classList.add('task-card', 'in-progress');
                } else if (status === 'COMPLETADA') {
                    card.classList.add('task-card', 'completed');
                }
            }
        }
    });

    // Añadir animación a los botones
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.05)';
            this.style.transition = 'transform 0.2s cubic-bezier(0.34, 1.56, 0.64, 1)';
        });
        
        button.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
            this.style.transition = 'transform 0.2s cubic-bezier(0.34, 1.56, 0.64, 1)';
        });
    });

    // Animación para las tarjetas al hacer hover
    taskCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            const cards = document.querySelectorAll('.task-card');
            cards.forEach(c => {
                if (c !== this) {
                    c.style.opacity = '0.7';
                    c.style.transform = 'scale(0.98)';
                }
            });
        });
        
        card.addEventListener('mouseleave', function() {
            const cards = document.querySelectorAll('.task-card');
            cards.forEach(c => {
                if (c !== this) {
                    c.style.opacity = '1';
                    c.style.transform = 'scale(1)';
                }
            });
        });
    });

    // Confirmación para eliminar tareas
    const deleteButtons = document.querySelectorAll('a[href*="/tasks/delete/"]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const taskId = this.getAttribute('href').split('/').pop();
            const taskTitle = this.closest('.task-card').querySelector('.card-title').textContent;
            
            showConfirmationModal({
                title: '¿Eliminar esta tarea?',
                body: `¿Estás seguro que deseas eliminar la tarea "${taskTitle}"?`,
                confirmText: 'Eliminar',
                cancelText: 'Cancelar',
                confirmClass: 'btn-danger',
                onConfirm: () => {
                    window.location.href = this.getAttribute('href');
                }
            });
        });
    });

    // Validación de formularios
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const titleInput = form.querySelector('#title');
            if (titleInput && titleInput.value.trim() === '') {
                e.preventDefault();
                showNotification('El título de la tarea no puede estar vacío', 'danger');
                titleInput.focus();
            }
        });
    });
    
    // Añadir efectos de transición al cargar la página
    document.body.classList.add('page-loaded');
});

// Modal de confirmación
function showConfirmationModal(options) {
    // Eliminar modal anterior si existe
    const existingModal = document.getElementById('confirmationModal');
    if (existingModal) {
        existingModal.remove();
    }
    
    // Crear elementos del modal
    const modalHtml = `
    <div class="modal fade" id="confirmationModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-effect border-0">
                <div class="modal-header border-0">
                    <h5 class="modal-title">${options.title}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ${options.body}
                </div>
                <div class="modal-footer border-0">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">${options.cancelText || 'Cancelar'}</button>
                    <button type="button" class="btn ${options.confirmClass || 'btn-primary'}" id="confirmBtn">${options.confirmText || 'Confirmar'}</button>
                </div>
            </div>
        </div>
    </div>
    `;
    
    // Añadir modal al DOM
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    
    // Inicializar el modal
    const modalElement = document.getElementById('confirmationModal');
    const modal = new bootstrap.Modal(modalElement);
    
    // Evento de confirmación
    document.getElementById('confirmBtn').addEventListener('click', function() {
        modal.hide();
        if (typeof options.onConfirm === 'function') {
            options.onConfirm();
        }
    });
    
    // Mostrar modal
    modal.show();
}

// Funciones de Drag and Drop
function allowDrop(ev) {
    ev.preventDefault();
    // Añadir clase de resaltado a la columna destino
    ev.currentTarget.classList.add('column-highlight');
    
    // Efecto de onda al entrar en una columna
    const ripple = document.createElement('div');
    ripple.classList.add('ripple-effect');
    ripple.style.left = (ev.offsetX - 50) + 'px';
    ripple.style.top = (ev.offsetY - 50) + 'px';
    ev.currentTarget.appendChild(ripple);
    
    setTimeout(() => {
        ripple.remove();
    }, 500);
}

function drag(ev) {
    // Guardar el ID de la tarea que se está arrastrando
    ev.dataTransfer.setData("taskId", ev.target.getAttribute('data-task-id'));
    
    // Añadir clase para estilo durante el arrastre
    ev.target.classList.add('dragging');
    
    // Añadir efecto de sombra a las otras tarjetas
    document.querySelectorAll('.task-card:not(.dragging)').forEach(card => {
        card.style.opacity = "0.5";
        card.style.transform = "scale(0.95)";
        card.style.transition = "opacity 0.3s ease, transform 0.3s ease";
    });
}

function drop(ev) {
    ev.preventDefault();
    
    // Eliminar clase de resaltado
    document.querySelectorAll('.task-column').forEach(column => {
        column.classList.remove('column-highlight');
    });
    
    // Restaurar opacidad de las otras tarjetas
    document.querySelectorAll('.task-card').forEach(card => {
        card.style.opacity = "1";
        card.style.transform = "scale(1)";
    });
    
    // Obtener el ID de la tarea
    const taskId = ev.dataTransfer.getData("taskId");
    const taskElement = document.getElementById('task-' + taskId);
    
    if (!taskElement) {
        console.error('No se encontró el elemento de la tarea:', taskId);
        return;
    }
    
    // Obtener el nuevo estado de la columna donde se soltó
    const newStatus = ev.currentTarget.getAttribute('data-status');
    
    // Eliminar clase de arrastre
    taskElement.classList.remove('dragging');
    
    // Evitar hacer la solicitud si se suelta en la misma columna
    const currentColumn = taskElement.closest('.task-column');
    if (currentColumn && currentColumn.getAttribute('data-status') === newStatus) {
        return;
    }
    
    // Mostrar un spinner o indicador de carga
    const loadingSpinner = document.createElement('div');
    loadingSpinner.className = 'spinner-border spinner-border-sm text-primary ms-2';
    loadingSpinner.setAttribute('role', 'status');
    
    // Verificar si existe el card-body antes de añadir el spinner
    const cardBody = taskElement.querySelector('.card-body');
    if (cardBody) {
        cardBody.appendChild(loadingSpinner);
    }
    
    // Actualizar las clases CSS de la tarjeta según el nuevo estado
    taskElement.classList.remove('pending', 'in-progress', 'completed');
    switch (newStatus) {
        case 'PENDIENTE':
            taskElement.classList.add('pending');
            break;
        case 'EN_PROGRESO':
            taskElement.classList.add('in-progress');
            break;
        case 'COMPLETADA':
            taskElement.classList.add('completed');
            break;
    }
    
    // Mover inmediatamente la tarjeta a la nueva columna para feedback visual inmediato
    ev.currentTarget.appendChild(taskElement);
    
    // Actualizar los contadores inmediatamente para feedback visual
    updateColumnCounters();
    
    // Enviar la actualización al servidor
    updateTaskStatus(taskId, newStatus)
        .then(updatedTask => {
            // Eliminar el spinner
            loadingSpinner.remove();
            
            // Añadir clase para indicar éxito con animación
            taskElement.classList.add('update-success');
            setTimeout(() => {
                taskElement.classList.remove('update-success');
            }, 1500);
            
            // Mostrar una notificación de éxito
            showNotification('Tarea actualizada con éxito', 'success');
        })
        .catch(error => {
            console.error('Error al actualizar el estado:', error);
            // Eliminar el spinner
            loadingSpinner.remove();
            
            // Revertir la clase CSS al estado original
            taskElement.classList.remove('pending', 'in-progress', 'completed');
            const originalStatus = currentColumn ? currentColumn.getAttribute('data-status') : '';
            switch (originalStatus) {
                case 'PENDIENTE':
                    taskElement.classList.add('pending');
                    break;
                case 'EN_PROGRESO':
                    taskElement.classList.add('in-progress');
                    break;
                case 'COMPLETADA':
                    taskElement.classList.add('completed');
                    break;
            }
            
            // En caso de error, devolver la tarea a su columna original
            taskElement.classList.add('update-error');
            
            if (currentColumn) {
                setTimeout(() => {
                    currentColumn.appendChild(taskElement);
                    // Actualizar contadores después de devolver la tarea
                    updateColumnCounters();
                    
                    setTimeout(() => {
                        taskElement.classList.remove('update-error');
                    }, 1000);
                }, 500);
            }
            
            // Mostrar una notificación de error
            showNotification('Error al actualizar la tarea: ' + (error.message || 'Conexión fallida'), 'danger');
        });
}

// Función para enviar la actualización al servidor
function updateTaskStatus(taskId, newStatus) {
    // Crear el token CSRF desde el meta tag (Spring Security)
    const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');
    
    const headers = {
        'Content-Type': 'application/json',
    };
    
    // Añadir token CSRF si está disponible
    if (token && header) {
        headers[header] = token;
    }
    
    return fetch('/api/tasks/' + taskId + '/status', {
        method: 'PATCH',
        headers: headers,
        body: JSON.stringify({ status: newStatus }),
        credentials: 'same-origin' // Importante para las cookies de sesión
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la actualización');
        }
        return response.json();
    })
    .then(updatedTask => {
        // Actualizar la UI con la información actualizada sin recargar la página
        
        // 1. Actualizar los contadores en cada columna
        updateColumnCounters();
        
        // 2. Actualizar la clase CSS de la tarjeta según el nuevo estado
        const taskCard = document.getElementById('task-' + taskId);
        if (taskCard) {
            // Primero, removemos todas las clases de estado
            taskCard.classList.remove('pending', 'in-progress', 'completed');
            
            // Luego, añadimos la clase correspondiente al nuevo estado
            switch (newStatus) {
                case 'PENDIENTE':
                    taskCard.classList.add('pending');
                    break;
                case 'EN_PROGRESO':
                    taskCard.classList.add('in-progress');
                    break;
                case 'COMPLETADA':
                    taskCard.classList.add('completed');
                    break;
            }
            
            // Actualizamos también el texto del estado (si existe)
            const statusText = taskCard.querySelector('.status-text');
            if (statusText) {
                statusText.textContent = newStatus;
            }
        }
        
        // 3. Devolver los datos actualizados
        return updatedTask;
    });
}

// Función para actualizar los contadores de las columnas
function updateColumnCounters() {
    // Contar tareas en cada columna
    const pendienteCount = document.querySelectorAll('#column-PENDIENTE .task-card').length;
    const enProgresoCount = document.querySelectorAll('#column-EN_PROGRESO .task-card').length;
    const completadaCount = document.querySelectorAll('#column-COMPLETADA .task-card').length;
    
    // Actualizar los contadores en el UI
    const pendienteCounter = document.querySelector('.card-header.bg-warning .badge');
    const enProgresoCounter = document.querySelector('.card-header.bg-info .badge');
    const completadaCounter = document.querySelector('.card-header.bg-success .badge');
    
    if (pendienteCounter) pendienteCounter.textContent = pendienteCount;
    if (enProgresoCounter) enProgresoCounter.textContent = enProgresoCount;
    if (completadaCounter) completadaCounter.textContent = completadaCount;
    
    // Mostrar/ocultar los mensajes de "No hay tareas" según corresponda
    toggleEmptyMessages('PENDIENTE', pendienteCount === 0);
    toggleEmptyMessages('EN_PROGRESO', enProgresoCount === 0);
    toggleEmptyMessages('COMPLETADA', completadaCount === 0);
}

// Función para mostrar/ocultar mensajes de columna vacía
function toggleEmptyMessages(columnStatus, isEmpty) {
    const emptyMessage = document.querySelector(`#column-${columnStatus} .empty-column-message`);
    if (emptyMessage) {
        emptyMessage.style.display = isEmpty ? 'block' : 'none';
    }
}

// Función para mostrar notificaciones
function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} notification glass-effect`;
    notification.innerHTML = `
        <div class="d-flex align-items-center">
            <i class="bi ${type === 'success' ? 'bi-check-circle-fill' : 'bi-exclamation-circle-fill'} me-2"></i>
            <span>${message}</span>
        </div>
    `;
    notification.style.position = 'fixed';
    notification.style.top = '20px';
    notification.style.right = '20px';
    notification.style.zIndex = '1050';
    notification.style.minWidth = '300px';
    notification.style.opacity = '0';
    notification.style.transform = 'translateX(50px)';
    document.body.appendChild(notification);
    
    // Mostrar la notificación con animación
    setTimeout(() => {
        notification.style.opacity = '1';
        notification.style.transform = 'translateX(0)';
        notification.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
    }, 10);
    
    // Eliminar la notificación después de 3 segundos
    setTimeout(() => {
        notification.style.opacity = '0';
        notification.style.transform = 'translateX(50px)';
        notification.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        setTimeout(() => notification.remove(), 500);
    }, 3000);
}

// Detectar tema oscuro del sistema
function detectDarkMode() {
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
        document.body.classList.add('dark-mode');
    }
    
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
        if (e.matches) {
            document.body.classList.add('dark-mode');
        } else {
            document.body.classList.remove('dark-mode');
        }
    });
}

// Inicializar detección de tema oscuro
detectDarkMode(); 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */