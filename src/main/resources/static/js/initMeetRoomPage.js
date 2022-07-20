const usersApiURL = 'http://localhost:8080/api/v1/users'
const eventsApiURL = 'http://localhost:8080/api/v1/events'
const eventsTable = document.getElementById('eventsTable')
const profileBlock = document.getElementById('profileBlock')
const MILLISECONDS_IN_ONE_WEEK = 604800000
const MILLISECONDS_IN_ONE_DAY = 86400000
const nameOfDayOfWeeks = ['Sun', 'Mon', 'Tues', 'Wed', 'Thur', 'Fri', 'Sat']
const nameOfMonths = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
let nameOfUser = 'none'

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

function getUserName() {
    sendRequest('GET', usersApiURL)
    .then(response => {
        nameOfUser = response.name

        const usernameProfile = document.createElement('h2')

        usernameProfile.textContent = nameOfUser

        profileBlock.appendChild(usernameProfile)
    })
    .catch(err => {
        console.log(err)
    })
}

function loadEvents(currentDate, currentWeek = 0) {
    // INIT TABLE WITH START VALUE
    eventsTable.replaceChildren()

    const headerRow = document.createElement('tr')

    for (let index = 0; index < 7; ++index) {
        const dayOfWeek = document.createElement('th')

        headerRow.appendChild(dayOfWeek)
    }

    eventsTable.appendChild(headerRow)

    for (let index = 0; index < 48; index++) {
        const tableRow = document.createElement('tr')

        const timeDisplay = document.createElement('td')

        // Set hourly list
        timeDisplay.textContent = Math.floor(index / 2).toString() + (
            ((index % 2) > 0) ? ':30' : ':00'
        )
        tableRow.appendChild(timeDisplay)

        // Set other cells intended for events
        for (let index = 0; index < 6; index++) {
            const eventDisplay = document.createElement('td')
            tableRow.appendChild(eventDisplay)
        }

        eventsTable.appendChild(tableRow)
    }

    // GET START OF WEEK (RU)
    let monday = currentDate.getTime() - (currentDate.getDay() - 1) * MILLISECONDS_IN_ONE_DAY + currentWeek * MILLISECONDS_IN_ONE_WEEK

    const headerColumns = eventsTable.childNodes[0].children

    for (let index = 1; index < headerColumns.length; ++index) {
        today = new Date(monday)
        headerColumns[index].textContent = nameOfDayOfWeeks[today.getDay()]
            + ' ' + today.getDate()
            + ' ' + nameOfMonths[today.getMonth()]
            + ' ' + today.getFullYear()

        monday = monday + MILLISECONDS_IN_ONE_DAY
    }

    sendRequest('GET', eventsApiURL + '?currentTime=' + encodeURIComponent(new Date(currentDate.getTime() + currentWeek * MILLISECONDS_IN_ONE_WEEK)))
    .then(response => {
        console.log(response)

        response.forEach(element => {
            const startEvent = new Date(element.startDateOfEvent)
            const endEvent = new Date(element.endDateOfEvent)

            let startColumnNum = startEvent.getDay()
            let startRowNum = startEvent.getHours() * 2 + (
                (startEvent.getMinutes() > 0) ? 1 : 0
            ) + 1 // 0 row is for day of week names

            const endColumnNum = endEvent.getDay()
            const endRowNum = endEvent.getHours() * 2 + (
                (endEvent.getMinutes() > 0) ? 1 : 0
            ) + 1 // 0 row is for day of week names

            const startTableCell = eventsTable.childNodes[startRowNum].childNodes[startColumnNum]
            const endTableCell = eventsTable.childNodes[endRowNum].childNodes[endColumnNum]

            const eventNameSpan = document.createElement('span')
            const eventHostSpan = document.createElement('span')
            const eventDetailsLink = document.createElement('a')

            eventNameSpan.textContent = 'Событие: ' + element.eventName
            eventHostSpan.textContent = 'Создатель: ' + element.membersOfEvent[0].username
            eventDetailsLink.textContent = 'Подробнее'

            eventDetailsLink.href = 'event/' + element.id

            const backgroundColor = '#' + Math.floor(Math.random()*16777215).toString(16);

            eventNameSpan.style.backgroundColor = backgroundColor
            eventHostSpan.style.backgroundColor = backgroundColor
            eventDetailsLink.style.backgroundColor = backgroundColor

            if (startTableCell.children.length > 0) {
                startTableCell.appendChild(document.createElement('br'))
            }

            startTableCell.appendChild(eventNameSpan)
            startTableCell.appendChild(document.createElement('br'))
            startTableCell.appendChild(eventHostSpan)
            startTableCell.appendChild(document.createElement('br'))
            startTableCell.appendChild(eventDetailsLink)

            const lastSpan = document.createElement('span')
            lastSpan.style.backgroundColor = backgroundColor
            endTableCell.appendChild(lastSpan)

            for (;(startRowNum != endRowNum) || (startColumnNum != endColumnNum);) {
                ++startRowNum;

                eventsTable.childNodes[startRowNum].childNodes[startColumnNum].style.backgroundColor = backgroundColor

                if (startRowNum == 48) {
                    startRowNum = 0;
                    ++startColumnNum;
                }
            }


        });
    })
    .catch(err => {
        console.log(err)
    })
}

getUserName()

loadEvents(new Date())