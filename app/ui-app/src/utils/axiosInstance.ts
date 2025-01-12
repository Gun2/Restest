import axios from "axios";

// axios 인스턴스 생성
const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    timeout: 5000, // 요청 타임아웃
    headers: {
        "Content-Type": "application/json",
    },
});

// 요청 인터셉터
axiosInstance.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 응답 인터셉터
axiosInstance.interceptors.response.use(
    (response) => {
        return response.data;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
