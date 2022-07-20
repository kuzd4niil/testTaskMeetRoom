// const usersApiURL = 'http://localhost:8080/api/v1/users'
// const eventsApiURL = 'http://localhost:8080/api/v1/events'
const addEventButton = document.getElementById('addEventButton')
const myModal = document.getElementById('myModal')
const addEventForm = document.getElementById('addEventForm')
const closeModalSpan = document.getElementById('closeModal')
const listOfUsers = document.getElementById('listOfUsers')
let isInitListOfUsers = false
let arrayOfUsers = []
const members = []

addEventButton.onclick = () => {
    if (!isInitListOfUsers) {
        sendRequest('GET', usersApiURL+'/all')
        .then(response => {
            arrayOfUsers = response
        
            response.forEach(element => {
                if (nameOfUser != element.username) {
                    const user = document.createElement('option')
                    user.text = element.username
                    listOfUsers.appendChild(user)
                } else {
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

addEventForm.onsubmit = event => {
    event.preventDefault();

    const collection = listOfUsers.selectedOptions;

    for (let i = 0; i < collection.length; i++) {
        // console.log(collection[i].text)
        arrayOfUsers.forEach(el => {
            if (collection[i].text == el.username) {
                members.push(el)
            }
        })
    }

    const ev = {
        eventName: addEventForm.elements['eventName'].value,
        description: addEventForm.elements['descriptionEvent'].value,
        startDateOfEvent: new Date(addEventForm.elements['start_date_of_event'].value),
        endDateOfEvent: new Date(addEventForm.elements['end_date_of_event'].value),
        membersOfEvent: [...members]
    }

    members.splice(1)

    sendRequest('POST', eventsApiURL, ev)
    .then(response => {
        console.log(response)
        myModal.style.display = 'none'
        loadEvents(ev.startDateOfEvent)
    })
    .catch(err => console.log(err))
}