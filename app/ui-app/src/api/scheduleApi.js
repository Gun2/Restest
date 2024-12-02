import axios from "axios";

const readAll = () => axios.get('/api/v1/schedules');
const update = (job) => axios.put('/api/v1/schedules', job);
const create = (job) => axios.post('/api/v1/schedules', job);
const deleteById = (id) => axios.delete(`/api/v1/schedules/${id}`);

export default {
    readAll,
    update,
    create,
    deleteById
}