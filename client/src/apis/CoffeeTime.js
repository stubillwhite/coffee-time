import axios from 'axios';

const protocol = process.env.NODE_ENV === 'production' ? 'https' : 'http';
const port = process.env.NODE_ENV === 'production' ? 443 : 8080;
const host = process.env.NODE_ENV === 'production' ? window.location.host : 'localhost';

export default axios.create({
    baseURL: `${protocol}://${host}:${port}/api`
});