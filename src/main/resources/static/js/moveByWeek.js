const backWeekArrow = document.getElementById('backWeekArrow')
const nextWeekArrow = document.getElementById('nextWeekArrow')
let currentWeek = 0;

backWeekArrow.onclick = () => {
    --currentWeek
    loadEvents(new Date(), currentWeek)
}

nextWeekArrow.onclick = () => {
    ++currentWeek
    loadEvents(new Date(), currentWeek)
}