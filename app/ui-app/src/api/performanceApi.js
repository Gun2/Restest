import axios from "axios";

const readMaxJob = () => axios.get('/api/v1/performances/max-job');
const readMaxInstance = () => axios.get('/api/v1/performances/max-instance');
const createPerformance = (param) => axios.post('/api/v1/performances', param);
const startPerformance = (param) => axios.post('/api/v1/performances/start', param);
const stopPerformance = (param) => axios.post('/api/v1/performances/stop', param);

export default {
    readMaxJob,
    readMaxInstance,
    createPerformance,
    startPerformance,
    stopPerformance
}