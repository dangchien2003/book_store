import { getAllCategory } from '@/features/category/categorySlice'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'
import { Autocomplete, TextField } from '@mui/material'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'

const Category = () => {
  const dispatch = useDispatch()
  const { categorys, done } = useSelector((state) => state.category)

  useEffect(() => {
    if (!done) {
      dispatch(getAllCategory())
    }
  }, [done, dispatch])

  const handleChangeValue = (event, newValue) => {
    const value = newValue ? newValue.id : null
    dispatch(incrementByFilter({ field: 'category', value: value }))
  }

  return (
    <Autocomplete
      disablePortal
      options={categorys}
      sx={{ mt: '15px', background: 'white' }}
      getOptionLabel={(option) => option.name}
      onChange={handleChangeValue}
      renderInput={(params) => <TextField {...params} label="Danh mục" variant="outlined" />}
    />
  )
}

export default Category
