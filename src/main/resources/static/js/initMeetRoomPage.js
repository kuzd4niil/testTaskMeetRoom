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

/**
 * Init profile block on main page. It just show name of client
 */
function getUserName() {
    sendRequest('GET', usersApiURL)
    .then(response => {
        console.log(response)
        nameOfUser = response.username

        const usernameProfile = document.createElement('h2')
        usernameProfile.style.color = '#00008B'
        usernameProfile.textContent = nameOfUser

        const userAvatar = document.createElement('img')
        userAvatar.src = "data:image/png;base64," + response.avatar
        userAvatar.width = '48'
        userAvatar.height = '48'

        profileBlock.appendChild(usernameProfile)
        profileBlock.appendChild(userAvatar)
    })
    .catch(err => {
        console.log(err)
    })
}

/**
 * Init table with start valueю
 * @param {Date} currentDate Current date that to calculate correct dates
 * @param {Number} currentWeek For moving by weeks
 */
function loadEvents(currentDate, currentWeek = 0) {
    // We redraw the entire table so as not to clear the tables from old data and styles.
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

    /*
     * Get start of week.
     */
    let currentDay = currentDate.getDay()
    // Transfer from a week that starts on Sun to a week that starts on Mon.
    currentDay = (currentDay == 0) ? 6 : (currentDay - 1)
    let monday = currentDate.getTime() - currentDay * MILLISECONDS_IN_ONE_DAY + currentWeek * MILLISECONDS_IN_ONE_WEEK

    // Assign date to days of week.
    const headerColumns = eventsTable.childNodes[0].children

    for (let index = 1; index < headerColumns.length; ++index) {
        today = new Date(monday)
        headerColumns[index].textContent = nameOfDayOfWeeks[today.getDay()]
            + ' ' + today.getDate()
            + ' ' + nameOfMonths[today.getMonth()]
            + ' ' + today.getFullYear()

        monday = monday + MILLISECONDS_IN_ONE_DAY
    }

    /*
     * Init table with events.
     * Row is time multiple of half of hour.
     * Column is day of week (from Monday to Saturday)
     */
    sendRequest('GET', eventsApiURL + '?currentTime=' + encodeURIComponent(new Date(currentDate.getTime() + currentWeek * MILLISECONDS_IN_ONE_WEEK)))
    .then(response => {
        response.forEach(element => {
            const startEvent = new Date(element.startDateOfEvent)
            const endEvent = new Date(element.endDateOfEvent)

            /*
             * Calculation of table coordinates by time and day of the week.
             */
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

            // Each event have own color (colors may repeat).
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

            // Paint over all the time cells in which the meeting will go.
            for (;(startRowNum != endRowNum) || (startColumnNum != endColumnNum);) {
                ++startRowNum;

                eventsTable.childNodes[startRowNum].childNodes[startColumnNum].style.backgroundColor = backgroundColor

                if (startRowNum == 48) {
                    startRowNum = 0;
                    ++startColumnNum;
                }
                if (startColumnNum == 7) {
                    break
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