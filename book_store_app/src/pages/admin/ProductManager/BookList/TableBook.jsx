import { formatCurrency } from '@/utils/format'
import { Chip, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material'
import { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { getAllBook } from '@/services/productService/bookService'
import { toastInfo } from '@/utils/toast'
import { useNavigate } from 'react-router-dom'

const statusTemplate = {
  'ON_SALE': {
    text: 'Đang mở',
    color: 'success'
  },
  'OUT_OF_SALE': {
    text: 'Dừng bán',
    color: 'error'
  },
  'HIDDEN': {
    text: 'Đang ẩn',
    color: 'warning'
  }
}

const genValueDiscount = (price, discount) => {
  return `${discount}% - ${formatCurrency(price - (price * discount / 100))}`
}

const genStatus = (statusId) => {
  const format = statusTemplate[statusId]

  return format ? <Chip label={format.text} color={format.color} /> : <Chip label={statusId} color='warning' />
}
let maxPage = null
let callAllow = true
const TableBook = () => {
  const [rows, setRows] = useState([])
  const [page, setPage] = useState(1)
  const filter = useSelector((state) => state.managerFilterBook.value)
  const finding = useSelector((state) => state.managerClickFindBook.value)
  const navigate = useNavigate()

  useEffect(() => {
    getBooks()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page])


  useEffect(() => {
    if (finding) {
      maxPage = null
      if (page == 1) {
        getBooks()
      } else {
        setPage(1)
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [finding])

  const getBooks = () => {
    // chặn call api và thay đổi lại giá trị
    if (!callAllow) {
      callAllow = true
      return
    }

    getAllBook(page, filter)
      .then(response => {
        if (response.data.result.length > 0) {
          setRows(response.data.result)
        } else if (page === 1) {
          toastInfo('Không có dữ liệu')
          setRows([])
          maxPage = 1
        } else {
          toastInfo('Đã đến trang cuối')
          maxPage = page - 1
          setPage(pre => pre - 1)
          // ngăn chặn call lại khi thay đổi page
          callAllow = false
        }
      })
      .catch(() => {
      })
  }

  const handleChangePage = (event, newPage) => {
    if (maxPage) {
      if (newPage > maxPage) {
        toastInfo('Không còn dữ liệu')
        return
      }
    }

    if (newPage > 0) {
      setPage(newPage)
    }
  }

  const handleClickViewDetail = (id) => {
    navigate('/manager/book/detail/' + id)
  }

  return (
    <Paper sx={{ width: '100%', mt: '10px' }}>
      <TableContainer component={Paper} sx={{ maxHeight: '500px' }}>
        <Table sx={{
          minWidth: '650px',
          overflow: 'scroll'
        }} aria-label="list book">
          <TableHead sx={{ backgroundColor: '#ffff', position: 'sticky', top: 0, zIndex: 1 }}>
            <TableRow sx={{
              '& .MuiTableCell-root': {
                fontWeight: 'bold'
              }
            }}>
              <TableCell>Ảnh</TableCell>
              <TableCell>Tên sách</TableCell>
              <TableCell>Giá bán</TableCell>
              <TableCell>Đang giảm</TableCell>
              <TableCell>Tác giả</TableCell>
              <TableCell>Trạng thái</TableCell>
              <TableCell>Số Trang</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {rows.map((row, index) => (
              <TableRow key={index}>
                <TableCell component="th" scope="row">
                  <img src={row.img} alt={row.name} style={{ maxWidth: '100px', maxHeight: '100px' }} />
                </TableCell>
                <TableCell sx={{ maxWidth: 200 }} onClick={() => { handleClickViewDetail(row.id) }}>{row.name}</TableCell>
                <TableCell >{formatCurrency(row.price)}</TableCell>
                <TableCell >{genValueDiscount(row.price, row.discount)}</TableCell>
                <TableCell >{row.authorName}</TableCell>
                <TableCell >
                  {genStatus(row.statusCode)}
                </TableCell>
                <TableCell >{row.pageCount}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        component="div"
        page={page}
        count={0}
        rowsPerPage={0}
        rowsPerPageOptions={[]}
        labelDisplayedRows={() => `Trang ${page}`}
        onPageChange={handleChangePage}
      />
    </Paper >
  )
}

export default TableBook


// function createData(img, name, price, discount, author, status, quantity) {
//   return { img, name, price, discount, author, status, quantity }
// }

// const rows = [
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt Frozen yoghurt Frozen yoghurt Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
//   createData('https://photo.znews.vn/w660/Uploaded/mdf_eioxrd/2021_07_06/2.jpg', 'Frozen yoghurt', 150000, 10, 'Đăng Chiến', 'ON_SALE', 12),
// ]
