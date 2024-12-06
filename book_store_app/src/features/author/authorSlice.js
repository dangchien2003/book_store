import { getAuthorInPage } from '@/services/productService/authorService'
import { sleep } from '@/utils/promise'
import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  authors: [],
  done: false
}

export const author = createSlice({
  name: 'author',
  initialState,
  reducers: {
    addAuthor: (state, action) => {
      state.authors = [...state.authors, ...action.payload]
    },
    setDone: (state, action) => {
      state.done = action.payload
    }
  }
})

export const { addAuthor, setDone } = author.actions

export const getAllAuthor = () => async (dispatch, getState) => {
  const { done } = getState().author

  if (done) {
    return
  }

  let page = 1
  try {
    // eslint-disable-next-line no-constant-condition
    while (true) {
      const response = await getAuthorInPage(page)

      if (response.data.result.length === 0) {
        dispatch(setDone(true))
        break
      }

      dispatch(addAuthor(response.data.result))
      page += 1

      await sleep(500)
    }
  } catch (error) {
    dispatch(setDone(true))
  }
}

export default author.reducer
