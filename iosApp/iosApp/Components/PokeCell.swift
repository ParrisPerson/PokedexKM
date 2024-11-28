//
//  PokeCell.swift
//  PokeDexCombine
//
//  Created by parris.person on 21/10/24.
//
import SwiftUI
import Shared

struct PokeCell: View {
    let pokemon: Pokemon
    let size: CGFloat 

    var body: some View {
        HStack {
            VStack (alignment: .leading){
                Text(pokemon.name.capitalized)
                    .font(.headline)
               // Text("# \(pokemon.id)")
                 //   .font(.caption)
            }
            Spacer()
          //  if let img = pokemon.imageUrl {
                PokemonImage(imageUrl: pokemon.imageUrl , size: size).allowsHitTesting(false)
           /* } else {
                PokemonImage(imageUrl: pokemon.sprites!.front_default!, size: size).allowsHitTesting(false)
            }*/
            
        }
    }
}
