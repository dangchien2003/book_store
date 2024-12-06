import { Autocomplete, TextField } from '@mui/material'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'
import { getAllAuthor } from '@/features/author/authorSlice'


const Author = () => {
  const dispatch = useDispatch()
  const { authors, done } = useSelector((state) => state.author)
  useEffect(() => {
    if (!done) {
      dispatch(getAllAuthor())
    }
  }, [done, dispatch])

  const handleChangeValue = (event, newValue) => {
    const value = newValue ? newValue.id : null
    dispatch(incrementByFilter({ field: 'author', value: value }))
  }

  return (
    <Autocomplete
      disablePortal
      options={authors}
      sx={{ mt: '15px', background: 'white' }}
      getOptionLabel={(option) => option.name}
      onChange={handleChangeValue}

      renderInput={(params) => <TextField {...params} label="Tác giả" variant="outlined" />}
      renderOption={(props, option) => (
        <li {...props} key={option.id}> { }
          {option.name}
        </li>
      )}
    />
  )
}

export default Author
