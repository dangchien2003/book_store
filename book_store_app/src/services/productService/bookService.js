import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

export async function getAllBook(page, filter) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllBook, {
    params: {
      page, name: filter.name, category: filter.category, publisher: filter.publisher, author: filter.author
    }
  })
}

export async function getBookDetail(id) {
  return await httpClient.get(API_PRODUCT_SERVICE.bookDetail + id)
}

export async function updateBookDetail(detail) {
  return await httpClient.patch(API_PRODUCT_SERVICE.updateBookDetail, detail)
}

