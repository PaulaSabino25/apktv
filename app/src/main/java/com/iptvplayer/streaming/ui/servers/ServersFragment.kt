package com.iptvplayer.streaming.ui.servers

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.iptvplayer.streaming.R
import com.iptvplayer.streaming.databinding.FragmentServersBinding
import com.iptvplayer.streaming.model.ServerConfig
import com.iptvplayer.streaming.repository.IPTVRepository
import kotlinx.coroutines.launch
import java.util.*

class ServersFragment : Fragment() {
    
    private var _binding: FragmentServersBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var repository: IPTVRepository
    private lateinit var serversAdapter: ServersAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServersBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = IPTVRepository(requireContext())
        setupRecyclerView()
        setupFab()
        loadServers()
    }
    
    private fun setupRecyclerView() {
        serversAdapter = ServersAdapter(
            onServerClick = { server ->
                showServerDialog(server)
            },
            onServerSync = { server ->
                syncServer(server)
            },
            onServerDelete = { server ->
                deleteServer(server)
            }
        )
        
        binding.recyclerViewServers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = serversAdapter
        }
    }
    
    private fun setupFab() {
        binding.fabAddServer.setOnClickListener {
            showServerDialog(null)
        }
    }
    
    private fun loadServers() {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val servers = repository.getAllServers()
                serversAdapter.submitList(servers)
                
                if (servers.isEmpty()) {
                    binding.textViewEmpty.visibility = View.VISIBLE
                    binding.recyclerViewServers.visibility = View.GONE
                } else {
                    binding.textViewEmpty.visibility = View.GONE
                    binding.recyclerViewServers.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao carregar servidores: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun showServerDialog(server: ServerConfig?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_server, null)
        val builder = AlertDialog.Builder(context)
            .setTitle(if (server == null) "Adicionar Servidor" else "Editar Servidor")
            .setView(dialogView)
            .setPositiveButton("Salvar", null)
            .setNegativeButton("Cancelar", null)
        
        if (server != null) {
            builder.setNeutralButton("Testar", null)
        }
        
        val dialog = builder.create()
        
        // Pre-fill fields if editing
        server?.let {
            // dialogView.findViewById<EditText>(R.id.editTextServerName).setText(it.name)
            // ... fill other fields
        }
        
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                saveServer(dialogView, server, dialog)
            }
            
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setOnClickListener {
                testServer(dialogView)
            }
        }
        
        dialog.show()
    }
    
    private fun saveServer(dialogView: View, existingServer: ServerConfig?, dialog: AlertDialog) {
        // Get values from dialog fields
        // val name = dialogView.findViewById<EditText>(R.id.editTextServerName).text.toString()
        // ... get other values
        
        if (true) { // Add validation
            val server = ServerConfig(
                id = existingServer?.id ?: UUID.randomUUID().toString(),
                name = "Server Test", // Replace with actual input
                m3uUrl = "http://example.com/playlist.m3u8", // Replace with actual input
                epgUrl = null,
                username = null,
                password = null,
                isActive = true,
                priority = 0,
                lastSync = 0
            )
            
            lifecycleScope.launch {
                try {
                    repository.addServer(server)
                    loadServers()
                    dialog.dismiss()
                    Toast.makeText(context, "Servidor salvo com sucesso", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Erro ao salvar servidor: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun testServer(dialogView: View) {
        // Test server connectivity
        Toast.makeText(context, "Testando servidor...", Toast.LENGTH_SHORT).show()
    }
    
    private fun syncServer(server: ServerConfig) {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val result = repository.syncServerChannels(server.id)
                
                result.fold(
                    onSuccess = { channelCount ->
                        Toast.makeText(
                            context,
                            "Sincronizado! $channelCount canais carregados",
                            Toast.LENGTH_LONG
                        ).show()
                        loadServers()
                    },
                    onFailure = { error ->
                        Toast.makeText(
                            context,
                            "Erro na sincronização: ${error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun deleteServer(server: ServerConfig) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Servidor")
            .setMessage("Deseja realmente excluir o servidor '${server.name}'?")
            .setPositiveButton("Excluir") { _, _ ->
                // Implement delete
                loadServers()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
