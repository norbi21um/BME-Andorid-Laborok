package hu.bme.aut.workplaceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.workplaceapp.ProfileActivity
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.ProfileDetailBinding

class DetailsProfileFragment : Fragment(){
    private lateinit var binding: ProfileDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProfileDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val person = DataManager.person
        binding.tvId.text = person.id
        binding.tvSSN.text = person.socialSecurityNumber
        binding.tvTaxId.text = person.taxId
        binding.tvRegistrationId.text = person.registrationId
    }
}