package com.example.mazaady.presentation.second_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mazaady.R
import com.example.mazaady.core.adapter.AdapterDelegateImpl
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.core.adapter.CommandListener
import com.example.mazaady.databinding.FragmentSecondScreenBinding
import com.example.mazaady.domain.models.BidderModel
import com.example.mazaady.presentation.second_screen.adapters.AuctionsAdapterDelegate
import com.example.mazaady.presentation.second_screen.adapters.BiddersAdapterDelegate

class SecondScreenFragment : Fragment(), CommandListener {

    private lateinit var binding: FragmentSecondScreenBinding
    private lateinit var biddersAdapterDelegate: AdapterDelegateImpl
    private lateinit var auctionsAdapterDelegate: AdapterDelegateImpl
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBiddersRecycler()
        setupAuctionsRecycler()
        binding.conversationIbSend.setOnClickListener { findNavController().navigate(R.id.firsScreenFragment) }
    }

    private fun setupBiddersRecycler() {

        biddersAdapterDelegate = AdapterDelegateImpl(
            this,
            listOf(BiddersAdapterDelegate())
        )

        binding.rvBidders.setHasFixedSize(true)
        binding.rvBidders.adapter = biddersAdapterDelegate
        biddersAdapterDelegate.submitList(
            listOf(
                BidderModel(id = 0),
                BidderModel(id = 1),
                BidderModel(id = 2),
                BidderModel(id = 3),
                BidderModel(id = 4),
                BidderModel(id = 5)
            )
        )
    }

    private fun setupAuctionsRecycler() {

        auctionsAdapterDelegate = AdapterDelegateImpl(
            this,
            listOf(AuctionsAdapterDelegate())
        )

        binding.rvAuctions.setHasFixedSize(true)
        binding.rvAuctions.adapter = auctionsAdapterDelegate
        auctionsAdapterDelegate.submitList(
            listOf(
                BidderModel(id = 0),
                BidderModel(id = 1),
                BidderModel(id = 2),
                BidderModel(id = 3),
                BidderModel(id = 4),
                BidderModel(id = 5)
            )
        )
    }

    override fun consumeCommand(action: BaseCommand) {
        TODO("Not yet implemented")
    }
}