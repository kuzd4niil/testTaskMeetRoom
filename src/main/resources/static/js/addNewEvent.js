// const usersApiURL = 'http://localhost:8080/api/v1/users'
// const eventsApiURL = 'http://localhost:8080/api/v1/events'
const addEventButton = document.getElementById('addEventButton')
const myModal = document.getElementById('myModal')
const addEventForm = document.getElementById('addEventForm')
const closeModalSpan = document.getElementById('closeModal')
const listOfUsers = document.getElementById('listOfUsers')
const modalException = document.getElementById('modalException')
let isInitListOfUsers = false // Flag indicating whether the list of users is initialized
let arrayOfUsers = [] // All users in database who can take part in meeting
const members = [] // Members of new event

/**
 * Handling a button click that show modal window for create new event
 */
addEventButton.onclick = () => {
    if (!isInitListOfUsers) {
        sendRequest('GET', usersApiURL+'/all')
        .then(response => {
            console.log(response)
            arrayOfUsers = response

            // Add all users to the list, except for the client of app
            response.forEach(element => {
                if (nameOfUser != element.username) {
                    const user = document.createElement('option')
                    user.text = element.username
                    listOfUsers.appendChild(user)
                } else {
                    // It is assumed that the creator of the meeting is already on the list of members of the meeting.
                    members.push(element)
                }
            });
        })
        .catch(err => {
            console.log(err)
        })

        isInitListOfUsers=true;
    }

    myModal.style.display = 'block'
}


closeModalSpan.onclick = () => {
    myModal.style.display = 'none'
}

window.onclick = function(event) {
    if (event.target == myModal) {
        myModal.style.display = 'none'
    }
}

/**
 * Send POST request to server for creating ew event
 */
addEventForm.onsubmit = event => {
    event.preventDefault();

    const collection = listOfUsers.selectedOptions;

    for (let i = 0; i < collection.length; i++) {
        arrayOfUsers.forEach(el => {
            if (collection[i].text == el.username) {
                members.push(el)
            }
        })
    }

    // JSON of event
    const ev = {
        eventName: addEventForm.elements['eventName'].value,
        description: addEventForm.elements['descriptionEvent'].value,
        startDateOfEvent: new Date(addEventForm.elements['start_date_of_event'].value),
        endDateOfEvent: new Date(addEventForm.elements['end_date_of_event'].value),
        membersOfEvent: [...members]
    }

    const validateString = ev.eventName.trim()

    // Check that the string is not empty and does not consist only of spaces.
    if (validateString.length == 0) {
        modalException.textContent = "Название события не может быть пустым"
        modalException.style.display = 'inline'
        return
    }

    // Only the client itself remains in the list for the next appointment creation.
    members.splice(1)

    sendRequest('POST', eventsApiURL, ev)
    .then(response => {
        modalException.style.display = 'none'
        myModal.style.display = 'none'
        loadEvents(ev.startDateOfEvent)
    })
    .catch(err => {
        modalException.textContent = err.message
        modalException.style.display = 'inline'
    })
}