import { getAuthorInPage } from '@/services/productService/authorService'
import { toastError } from '@/utils/toast'
import { Autocomplete, TextField } from '@mui/material'
import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'

const Author = () => {
  const dispatch = useDispatch()
  const [authors, setAuthor] = useState([])
  const [page, setPage] = useState(1)

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      getAuthorInPage(page)
        .then(response => {
          if (response.data.result.length === 0) {
            return
          }
          setAuthor(authors.concat(response.data.result))
          setPage(currentPage => currentPage + 1)
        })
        .catch(() => {
          toastError('Có lỗi xảy ra')
        })
    }, 1500)
    return () => clearTimeout(timeoutId)
  }, [page, authors])

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
