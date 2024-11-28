import SwiftUI
import Shared


struct ContentView: View {
    private let viewModel = PokemonViewModel(httpClientProvider: HttpClientProviderImpl())
    @State private var pokemonList: [Pokemon] = []

    var body: some View {
        Text("PokeDex")
        List(pokemonList, id: \.name) { pokemon in
             PokeCell(pokemon: pokemon, size: 150)
        }
        .onAppear {
            viewModel.pokemonList.watch { list in
                if let pokemonArray = list as? [Pokemon] {
                    self.pokemonList = pokemonArray
                }
            }
        }
        .onDisappear {
            viewModel.onCleared()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
