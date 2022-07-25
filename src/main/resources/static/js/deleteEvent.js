const eventsApiURL = 'http://localhost:8080/api/v1/events'
const deleteEvent = document.getElementById('deleteEvent')

/**
 * Send request to server
 * @param {string} method Name of HTTP method
 * @param {string} url REST API URL
 * @param {JSON} body Body
 * @returns {Promise} Promise
 */
 function sendRequest(method, url, body = null) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest()

        xhr.open(method, url)

        xhr.responseType = 'json'

        xhr.setRequestHeader('Content-Type', 'application/json')

        xhr.onload = () => {
            if (xhr.status >= 400) {
                reject(xhr.response)
            } else {
                resolve(xhr.response)
            }
        }

        xhr.onerror = () => {
            reject(xhr.response)
        }

        xhr.send(JSON.stringify(body))
    })
}

/**
 * Handleing button that delete event
 */
deleteEvent.onclick = () => {
    sendRequest('DELETE', eventsApiURL + '/' + eventId.textContent)
    .then(() => {
        const statusReq = document.createElement('h1')
        statusReq.style.color = '#FF0000'
        statusReq.textContent = 'СОБЫТИЕ УДАЛЕНО'

        // Show message about event was deleting in 1 second
        document.body.insertBefore(statusReq, document.body.childNodes[0])
        setTimeout(() => {
            window.location.href = 'http://localhost:8080'
        }, 1000)
    })
    .catch(err => {
        console.log(err)
    })
}