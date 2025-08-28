package com.example.stylish.data.repository



import android.util.Log
import com.example.stylish.data.local.dto.AddressDao
import com.example.stylish.data.local.dto.BankAccountDao
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.data.remote.FirebaseService
import com.example.stylish.domain.repository.AddressAccountRepository
import com.example.stylish.util.Result

class AddressAccountRepositoryImpl(
    private val addressDao: AddressDao,
    private val bankAccountDao: BankAccountDao,
    private val firebaseService: FirebaseService
) : AddressAccountRepository {

    override suspend fun addAddress(address: AddressEntity): Result<String> {
        return try {
            addressDao.insert(address)
            firebaseService.saveAddress(address)
            Result.Success("Address saved successfully")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to save address")
        }
    }

    override suspend fun getAddress(): Result<AddressEntity> {
        return try {
            val local = addressDao.getAddress()
            if (local != null) {
                return Result.Success(local)
            }
            val remote = firebaseService.getAddress()
            if (remote != null) {
                addressDao.insert(remote)
                Result.Success(remote)
            } else {

                Result.Failure("No address found")
            }
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to fetch address")
        }
    }

    override suspend fun addBankAccount(account: BankAccountEntity): Result<String> {
        return try {
            bankAccountDao.insert(account)
            firebaseService.saveBankAccount(account)
            Result.Success("Bank account saved successfully")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to save bank account")
        }
    }

    override suspend fun getBankAccount(): Result<BankAccountEntity> {
        return try {
            val local = bankAccountDao.getAccount()
            if (local != null) return Result.Success(local)

            val remote = firebaseService.getBankAccount()

            if (remote != null) {
                bankAccountDao.insert(remote)

                Result.Success(remote)
            } else {

                Result.Failure("No bank account found")
            }
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to fetch bank account")
        }
    }

    override suspend fun logout(): Result<String> {
        return  try {
            bankAccountDao.clear()       // BankAccountDao me already clear() hai
            addressDao.clear()
            firebaseService.logout()
            Result.Success("User logout success")
        }catch (e: Exception){
            Result.Failure(e.message?:"Logout failed")
        }
    }
}
