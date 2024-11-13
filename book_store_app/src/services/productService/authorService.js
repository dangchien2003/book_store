import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

async function getAuthorInPage(page) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllAuthor, {
    params: {
      page
    }
  })
}


export {
  getAuthorInPage
}
