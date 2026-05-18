package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.BorrowDao;
import com.library.dao.FineDao;
import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.BorrowResponse;
import com.library.dto.response.FineResponse;
import com.library.model.Fine;
import com.library.model.FineStatus;
import com.library.service.FineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class FineServiceImpl implements FineService {
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    private final FineDao fineDao;
    private final BorrowDao borrowDao;

    public FineServiceImpl(FineDao fineDao, BorrowDao borrowDao) {
        this.fineDao = fineDao;
        this.borrowDao = borrowDao;
    }

    public FineResponse createFineForLateReturn(FineRequest fineRequest) throws Exception {
        Connection conn = null;
        Fine fine = new Fine();
        fine.setBorrowId(fineRequest.getBorrowId());
        log.info("Late return detected for borrowId={}, fine created", fineRequest.getBorrowId());
        try {
            conn = pool.getConnection();

            BorrowResponse borrow = borrowDao.getBorrowById(conn, fineRequest.getBorrowId());
            if (borrow == null) {
                log.warn("Borrow not found with id={}", fineRequest.getBorrowId());
                throw new Exception("Borrow not found");
            }
            fine.setUserId(borrow.getUserId());

            int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
            if (daysLate <= 0) {
                log.warn("Borrow is not late");
                throw new Exception("Borrow is not late");
            }

            double fineAmount = daysLate * 10000;
            fine.setFineAmount(fineAmount);
            fine.setDaysLate(daysLate);

            fineDao.insert(conn, fine);

            conn.commit();

            log.info("Created fine successfully for borrowId={}", fineRequest.getBorrowId());

            return fineDao.getFineById(conn, fine.getId());
        } catch (Exception e) {
            log.error("Error creating fine for borrowId={} : {}", fineRequest.getBorrowId(), e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public FineResponse viewFineById(int id) throws Exception {
        Connection conn = null;
        log.info("View fine with id = {}", id);
        try {
            conn = pool.getConnection();

            FineResponse fine = fineDao.getFineById(conn, id);

            if (fine == null) {
                log.warn("Fine not found with id={}", id);
                throw new Exception("Fine not found");
            }

            if (fine.getStatus() == FineStatus.PAID) {
                log.info("Fine with id = {} is already PAID. Returning as is.", id);
                return fine;
            }

            BorrowResponse borrow = borrowDao.getBorrowById(conn, fine.getBorrowId());

            int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());

            if (daysLate <= 0) {
                log.warn("Borrow is not late");
                throw new Exception("Borrow is not late");
            }

            double fineAmount = daysLate * 10000;

            if (daysLate != fine.getDaysLate()) {
                fine.setDaysLate(daysLate);

                fine.setFineAmount(fineAmount);

                fineDao.updateDaysLate(conn, fine);

                conn.commit();

                log.info("Fine updated and found successfully with id = {}", id);
            }

            log.info("Fine found successfully with id = {}", id);

            return fine;

        } catch (Exception e) {
            log.error("Error viewing fine with id = {} : {}", id, e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void deleteFine(int id) throws Exception {
        Connection conn = null;
        log.info("Delete fine with id = {}", id);
        try {
            conn = pool.getConnection();

            FineResponse existingFine = fineDao.getFineById(conn, id);
            if (existingFine == null) {
                log.warn("Fine not found with id={}", id);
                throw new Exception("Fine not found");
            }

            fineDao.delete(conn, id);

            conn.commit();

            log.info("Deleted fine successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Error deleting fine with id = {} : {}", id, e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void softDeleteFine(int id) throws Exception {
        Connection conn = null;
        log.info("Soft delete fine with id = {}", id);
        try {
            conn = pool.getConnection();

            FineResponse existingFine = fineDao.getFineById(conn, id);
            if (existingFine == null) {
                log.warn("Fine not found with id={}", id);
                throw new Exception("Fine not found");
            }

            fineDao.softDelete(conn, id);

            conn.commit();

            log.info("Soft deleted fine successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Error soft deleting fine with id = {} : {}", id, e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public FineResponse payFine(int id) throws Exception {
        Connection conn = null;
        log.info("Pay fine with id = {}", id);
        try {
            conn = pool.getConnection();

            FineResponse existingFine = fineDao.getFineById(conn, id);
            if (existingFine == null) {
                log.warn("Fine not found with id={}", id);
                throw new Exception("Fine not found");
            }

            fineDao.payFine(conn, id);

            conn.commit();

            log.info("Fine paid successfully with id = {}", id);

            return fineDao.getFineById(conn, id);
        } catch (Exception e) {
            log.error("Error paying fine with id = {} : {}", id, e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<FineResponse> filter(FineFilterRequest filter) throws Exception {
        Connection conn = null;
        log.info(
                "View fines with filter: status={}, page = {}, size = {}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );
        try {
            conn = pool.getConnection();

            List<FineResponse> fines = fineDao.getFinesWithFilter(conn, filter);

            for (FineResponse fine : fines) {

                if (fine.getStatus() == FineStatus.PAID) {
                    continue;
                }

                BorrowResponse borrow = borrowDao.getBorrowById(conn, fine.getBorrowId());

                int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());

                if (daysLate <= 0) {
                    log.warn("Borrow is not late");
                    throw new Exception("Borrow is not late");
                }

                double fineAmount = daysLate * 10000;

                if (daysLate != fine.getDaysLate()) {
                    fine.setDaysLate(daysLate);

                    fine.setFineAmount(fineAmount);

                    fineDao.updateDaysLate(conn, fine);
                }
            }

            conn.commit();

            log.info("fines found successfully, total= {}", fines.size());

            return fines;
        } catch (Exception e) {
            log.error("view fines with filter failed: {}", e.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
