import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

export async function getAuthorInPage(page) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllAuthor, {
    params: {
      page
    }
  })
}

export async function getAuthorById(id) {
  return await httpClient.get(API_PRODUCT_SERVICE.getInfoAuthor, {
    params: {
      id: id
    }
  })
}

