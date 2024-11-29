import axios from "axios";

const readAllResponseSuccess= (schedulerId) => axios.get(`/api/v1/scheduler/${schedulerId}/responses/successes`);
const readAllResponseFailure = (schedulerId) => axios.get(`/api/v1/scheduler/${schedulerId}/responses/failures`);

export default {
    readAllResponseSuccess,
    readAllResponseFailure
}