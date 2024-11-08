import axios from 'axios'
import { API_BASE_URL } from './apiConfig'

const httpClient = axios.create({
  baseURL: `${API_BASE_URL}`,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 30000
})

export default httpClient