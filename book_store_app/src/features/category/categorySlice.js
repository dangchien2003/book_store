import { getCategoryInPage } from '@/services/productService/categoryService'
import { sleep } from '@/utils/promise'
import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  categorys: [],
  done: false
}

export const category = createSlice({
  name: 'category',
  initialState,
  reducers: {
    addCategory: (state, action) => {
      state.categorys = [...state.categorys, ...action.payload]
    },
    setDone: (state, action) => {
      state.done = action.payload
    }
  }
})

export const { addCategory, setDone } = category.actions

export const getAllCategory = () => async (dispatch, getState) => {
  const { done } = getState().category

  if (done) {
    return
  }

  let page = 1
  try {
    // eslint-disable-next-line no-constant-condition
    while (true) {
      const response = await getCategoryInPage(page)

      if (response.data.result.length === 0) {
        dispatch(setDone(true))
        break
      }

      dispatch(addCategory(response.data.result))
      page += 1

      await sleep(500)
    }
  } catch (error) {
    dispatch(setDone(true))
  }
}

export default category.reducer
