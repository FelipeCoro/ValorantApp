package com.sokah.valorantapp.network

import android.util.Log
import com.sokah.valorantapp.model.agents.AgentModel
import com.sokah.valorantapp.model.BaseModel
import com.sokah.valorantapp.model.weapons.WeaponModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValorantApiService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAgents(): BaseModel<MutableList<AgentModel>> {

        return withContext(Dispatchers.IO) {

            val response = retrofit.create(ValorantApi::class.java).getAgents()


            response.body()!!
        }

    }

    suspend fun getAgent(agentUuid:String,language:String) :BaseModel<AgentModel>{

        Log.e("TAG", language )
        return  withContext(Dispatchers.IO) {


            val response = retrofit.create(ValorantApi::class.java).getAgent(agentUuid,language)

            Log.e("TAG", response.toString())
            response.body()!!

        }


    }

    suspend fun getWeapons():BaseModel<MutableList<WeaponModel>>{

        return withContext(Dispatchers.IO) {

            val response = retrofit.create(ValorantApi::class.java).getWeapons()
            response.body()!!
        }
    }
}