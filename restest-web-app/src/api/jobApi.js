import axios from "axios";

const readAll = () => axios.get('/api/v1/jobs');
const update = (job) => axios.put('/api/v1/jobs', job);
const create = (job) => axios.post('/api/v1/jobs', job);
const deleteById = (id) => axios.delete(`/api/v1/jobs/${id}`);

const jobApi = {
    readAll,
    update,
    create,
    deleteById
}

export default jobApi;